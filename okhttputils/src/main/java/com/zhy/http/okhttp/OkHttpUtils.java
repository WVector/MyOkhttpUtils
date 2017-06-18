package com.zhy.http.okhttp;

import android.content.Context;

import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.GlobalParams;
import com.zhy.http.okhttp.builder.HeadBuilder;
import com.zhy.http.okhttp.builder.OtherRequestBuilder;
import com.zhy.http.okhttp.builder.PostFileBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.builder.PostStringBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.intercepter.HttpLoggingInterceptor;
import com.zhy.http.okhttp.intercepter.NetInterceptor;
import com.zhy.http.okhttp.request.RequestCall;
import com.zhy.http.okhttp.utils.IndexOutOfBounds;
import com.zhy.http.okhttp.utils.Platform;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by zhy on 15/8/17.
 */
public class OkHttpUtils {
    public static final long DEFAULT_MILLISECONDS = 10_000L;
    private volatile static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Platform mPlatform;
    private Context mContext;
    //    private HttpParams mHttpParams;
    private GlobalParams mGlobalParams = new GlobalParams() {
        @Override
        public Map<String, String> addParams() {
            return new LinkedHashMap<>();
        }
    };

    public OkHttpUtils(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        } else {
            mOkHttpClient = okHttpClient;
        }

        mPlatform = Platform.get();
    }

//    public OkHttpUtils setHttpParams(HttpParams httpParams) {
//        mHttpParams = httpParams;
//        return this;
//    }

    public static OkHttpUtils initClient(OkHttpClient okHttpClient) {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtils(okHttpClient);
                }
            }
        }
        return mInstance;
    }

    public static OkHttpUtils getInstance() {
        return initClient(null);
    }

    public static GetBuilder get() {
        return new GetBuilder();
    }

    public static PostStringBuilder postString() {
        return new PostStringBuilder();
    }

    public static PostFileBuilder postFile() {
        return new PostFileBuilder();
    }

    public static PostFormBuilder post() {
        return new PostFormBuilder();
    }

    public static OtherRequestBuilder put() {
        return new OtherRequestBuilder(METHOD.PUT);
    }

    public static HeadBuilder head() {
        return new HeadBuilder();
    }

    public static OtherRequestBuilder delete() {
        return new OtherRequestBuilder(METHOD.DELETE);
    }

    public static OtherRequestBuilder patch() {
        return new OtherRequestBuilder(METHOD.PATCH);
    }

    public OkHttpUtils addInterceptor(Interceptor interceptor) {
        if (mOkHttpClient != null && interceptor != null) {
            mOkHttpClient = mOkHttpClient.newBuilder().addInterceptor(interceptor).build();
        }
        return this;
    }

    public OkHttpUtils addInterceptors(List<Interceptor> interceptors) {
        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                addInterceptor(interceptor);
            }
        }
        return this;
    }

    public OkHttpUtils init(Context context) {

        IndexOutOfBounds.bound();

        mContext = context;


        //添加网络异常拦截器
        if (mOkHttpClient != null) {
            mOkHttpClient = mOkHttpClient.newBuilder().addInterceptor(new NetInterceptor()).build();
        }


        return this;
    }

    public OkHttpUtils timeout(long timeout) {
        if (mOkHttpClient != null) {
            mOkHttpClient = mOkHttpClient.newBuilder()
                    .connectTimeout(timeout, TimeUnit.MILLISECONDS)
                    .readTimeout(timeout, TimeUnit.MILLISECONDS)
                    .writeTimeout(timeout, TimeUnit.MILLISECONDS)
                    .build();
        }
        return this;
    }

    /**
     * 设置https协议ssl的证书
     *
     * @param cerName assets证书的全称
     * @param context 环境变量
     * @return this
     */
    public OkHttpUtils sslSocketFactory(String cerName, Context context) {
        SSLSocketFactory sslSocketFactory = HttpsUtils.getSSLSocketFactory(cerName, context);
        if (mOkHttpClient != null && sslSocketFactory != null) {
            mOkHttpClient = mOkHttpClient.newBuilder()
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    })
                    .sslSocketFactory(sslSocketFactory)
                    .build();
        }
        return this;
    }

    public OkHttpUtils debug(boolean isDebug, String tag) {
        if (isDebug) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(tag);
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            if (mOkHttpClient != null) {
                mOkHttpClient = mOkHttpClient.newBuilder().addInterceptor(loggingInterceptor).build();
            }
        }

        return this;
    }

    public Executor getDelivery() {
        return mPlatform.defaultCallbackExecutor();
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public void execute(final RequestCall requestCall, Callback callback) {
        if (callback == null)
            callback = Callback.CALLBACK_DEFAULT;
        final Callback finalCallback = callback;
        final int id = requestCall.getOkHttpRequest().getId();

        requestCall.getCall().enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                sendFailResultCallback(call, null, e, finalCallback, id);
            }

            @Override
            public void onResponse(final Call call, final Response response) {
                try {
                    if (call.isCanceled()) {
                        sendFailResultCallback(call, response, new IOException("Canceled!"), finalCallback, id);
                        return;
                    }


                    if (!finalCallback.validateResponse(response, id)) {
                        sendFailResultCallback(call, response, new IOException("request failed , reponse's code is : " + response.code()), finalCallback, id);
                        return;
                    }

                    Object o = finalCallback.parseNetworkResponse(response, id);
                    sendSuccessResultCallback(o, finalCallback, id);
                } catch (Exception e) {
                    sendFailResultCallback(call, response, e, finalCallback, id);
                } finally {
                    if (response.body() != null)
                        response.body().close();
                }

            }
        });
    }

    public void sendFailResultCallback(final Call call, final Response response, final Exception e, final Callback callback, final int id) {
        if (callback == null) return;

        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onError(call, response, e, id);
                callback.onAfter(id);
            }
        });
    }

    public void sendSuccessResultCallback(final Object object, final Callback callback, final int id) {
        if (callback == null) return;
        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(object, id);
                callback.onAfter(id);
            }
        });
    }

    public void cancelTag(Object tag) {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    public Context getContext() {
        if (mContext == null) {
            throw (new NullPointerException("必须在application中进行init初始化"));
        }
        return mContext;
    }

    public GlobalParams getGlobalParams() {
        return mGlobalParams;
    }

    public OkHttpUtils setGlobalParams(GlobalParams globalParams) {
        mGlobalParams = globalParams;
        return this;
    }

//    public HttpParams getHttpParams() {
//        return mHttpParams;
//    }

    public static class METHOD {
        public static final String HEAD = "HEAD";
        public static final String DELETE = "DELETE";
        public static final String PUT = "PUT";
        public static final String PATCH = "PATCH";
    }
}

