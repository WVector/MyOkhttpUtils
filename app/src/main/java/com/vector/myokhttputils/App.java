package com.vector.myokhttputils;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * Created by Vector
 * on 2017/2/6 0006.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpUtils.getInstance()
                .init(this)
                .debug(true, "okhttp")
                .timeout(20 * 1000L)
                .enableTlS12();
//                .setGlobalParams(new MyGlobalParams());
//                .setCertificate("8445.cer",this);
//                .setCertificates(getOpenIn("8445.cer"));
//                .setCertificates(null);


        OkHttpClient.Builder builder = OkGo.getInstance().init(this).getOkHttpClient().newBuilder();


        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(HttpsUtils.chooseTrustManager(HttpsUtils.prepareTrustManager(getOpenIn("8445.cer"))));
        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        builder.hostnameVerifier(HttpsUtils.UnSafeHostnameVerifier);

    }

    private InputStream getOpenIn(String name) {
        try {
            return getAssets().open(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
