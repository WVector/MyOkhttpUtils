package com.vector.myokhttputils;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.cookie.CookieJarImpl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.CookieJar;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
{

    private String mBaseUrl = "http://192.168.31.242:8888/okHttpServer/";

    private static final String TAG = "MainActivity";

    private TextView mTv;
    private ImageView mImageView;
    private ProgressBar mProgressBar;


    public class MyStringCallback extends StringCallback
    {
        @Override
        public void onBefore(Request request, int id)
        {
            setTitle("loading...");
        }

        @Override
        public void onAfter(int id)
        {
            setTitle("Sample-okHttp");
        }

        @Override
        public void onError(Call call, Response response, Exception e, int id)
        {
            e.printStackTrace();
            String errorMessage = validateError(e, response);
            mTv.setText("onError:" + errorMessage);
        }

        @Override
        public void onResponse(String response, int id)
        {
            Log.e(TAG, "onResponse：complete");
            mTv.setText("onResponse:" + response);

            switch (id)
            {
                case 100:
                    Toast.makeText(MainActivity.this, "http", Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    Toast.makeText(MainActivity.this, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void inProgress(float progress, long total, int id)
        {
            Log.e(TAG, "inProgress:" + progress);
            mProgressBar.setProgress((int) (100 * progress));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTv = (TextView) findViewById(R.id.id_textview);
        mImageView = (ImageView) findViewById(R.id.id_imageview);
        mProgressBar = (ProgressBar) findViewById(R.id.id_progress);
        mProgressBar.setMax(100);
    }

    public void getHtml(View view)
    {
//        String url = "http://www.zhiyun-tech.com/App/Rider-M/changelog-zh.txt";
//        url="http://httpbin.org/hidden-basic-auth/user/passwd";
////        OkHttpUtils
////                .get()
////                .url(url)
////                .id(100)
////                .build()
////                .execute(new MyStringCallback());
//
//        OkHttpUtils.getInstance().getOkHttpClient().newCall(new Request.Builder().url(url).get().build()).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e(TAG,"onFailure : "+e);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                Log.e(TAG,"response : "+response);
//            }
//        });

        String url = "http://10.222.5.191:8080/nmyd/xcysoft/mobile/zdld/zdldFileGetdy.ht?code=fileget&token=m2m8csk8g38qoa5v2l5u68yyd8o5drki&OperType=Android&deviceType=MI%204C%7C6.0.1";
        String url1 = "http://10.222.5.191:8080/nmyd/xcysoft/mobile/zdld/zdldList.ht?code=fileget&action=2&token=ho1rwki1w76v5na5uoomgk0pplkdbleh&OperType=Android&deviceType=ZTE%20A2015%7C6.0.1";


        String url3 = "http://10.222.5.191:8080/nmyd/xcysoft/mobile/zdld/zdldList.ht?code=filesend&action=2&token=gr379glbg28w3ydzbs8kg63exol9mo6b&OperType=Android&deviceType=MI%204C%7C6.0.1";

//        OkHttpUtils.getInstance().getOkHttpClient().ss

//        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
//
//
//
////        CookieJarImpl cookieJar1 = new CookieJarImpl(new MemoryCookieStore());
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
//                .readTimeout(10000L, TimeUnit.MILLISECONDS)
//
//                .hostnameVerifier(new HostnameVerifier()
//                {
//                    @Override
//                    public boolean verify(String hostname, SSLSession session)
//                    {
//                        return true;
//                    }
//                })
//
//                .build();
//
//
//
//        OkHttpUtils.initClient(okHttpClient);
        OkHttpUtils.get().url(url3).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Response response, Exception e, int id) {

                Log.d(TAG, "error");
            }

            @Override
            public void onResponse(String response, int id) {
                Log.d(TAG, "1----" +response);

            }
        });
        OkHttpUtils.get().url(url1).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Response response, Exception e, int id) {
                Log.d(TAG, "error");
            }

            @Override
            public void onResponse(String response, int id) {
                Log.d(TAG,"2----" +response);

            }
        });
    }


    public void postString(View view)
    {
        String url = mBaseUrl + "user!postString";
        OkHttpUtils
                .postString()
                .url(url)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(new Gson().toJson(new User("zhy", "123")))
                .build()
                .execute(new MyStringCallback());

    }

    public void postFile(View view)
    {
        File file = new File(Environment.getExternalStorageDirectory(), "messenger_01.png");
        if (!file.exists())
        {
            Toast.makeText(MainActivity.this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = mBaseUrl + "user!postFile";
        OkHttpUtils
                .postFile()
                .url(url)
                .file(file)
                .build()
                .execute(new MyStringCallback());


    }

    public void getUser(View view)
    {
        String url = mBaseUrl + "user!getUser";
        OkHttpUtils
                .post()//
                .url(url)//
                .addParams("username", "hyman")//
                .addParams("password", "123")//
                .build()//
                .execute(new UserCallback()
                {
                    @Override
                    public void onError(Call call, Response response, Exception e, int id)
                    {
                        mTv.setText("onError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(User response, int id)
                    {
                        mTv.setText("onResponse:" + response.username);
                    }
                });
    }


    public void getUsers(View view)
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", "zhy");
        String url = mBaseUrl + "user!getUsers";
        OkHttpUtils//
                .post()//
                .url(url)//
//                .params(params)//
                .build()//
                .execute(new ListUserCallback()//
                {
                    @Override
                    public void onError(Call call, Response response, Exception e, int id)
                    {
                        mTv.setText("onError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(List<User> response, int id)
                    {
                        mTv.setText("onResponse:" + response);
                    }
                });
    }


    public void getHttpsHtml(View view)
    {
        String url = "https://218.202.107.139:8443/nmyd/xcysoft/mobile/mobileEncryptLogin.ht?device=NkHAHkh2p%2FdxCkzKyuAiwz45lcmziZ4m&password=wPYXFetjQ%2B8%3D&describe=nAm4E81w2YOKnnE7t8S0vQ%3D%3D&username=jN99lavNBat4DLDqO0nhkA%3D%3D&token=HZ4KqW9b96baWPMvTnNARrqRQs2M0t6dUljQg%2FT7IAozhWRPrcPJQw%3D%3D&clientVersion=2041&deviceType=MI%204C%7C6.0.1&production=true&OperType=Android";

        String url1 = "https://kyfw.12306.cn/otn/";
//                "https://12
//        url =3.125.219.144:8443/mobileConnect/MobileConnect/authLogin.action?systemid=100009&mobile=13260284063&pipe=2&reqtime=1422986580048&ispin=2";
        OkHttpUtils
                .get()//
                .url(url1)//
                .id(101)
                .build()//
                .execute(new MyStringCallback());

    }

    public void getImage(View view)
    {
        mTv.setText("");
        String url = "http://images.csdn.net/20150817/1.jpg";
        OkHttpUtils
                .get()//
                .url(url)//
                .tag(this)//
                .build()//
                .connTimeOut(20000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(new BitmapCallback()
                {
                    @Override
                    public void onError(Call call, Response response, Exception e, int id)
                    {
                        mTv.setText("onError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Bitmap bitmap, int id)
                    {
                        Log.e("TAG", "onResponse：complete");
                        mImageView.setImageBitmap(bitmap);
                    }
                });
    }


    public void uploadFile(View view)
    {

        File file = new File(Environment.getExternalStorageDirectory(), "messenger_01.png");
        if (!file.exists())
        {
            Toast.makeText(MainActivity.this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("username", "张鸿洋");
        params.put("password", "123");

        Map<String, String> headers = new HashMap<>();
        headers.put("APP-Key", "APP-Secret222");
        headers.put("APP-Secret", "APP-Secret111");

        String url = mBaseUrl + "user!uploadFile";

        OkHttpUtils.post()//
                .addFile("mFile", "messenger_01.png", file)//
                .url(url)//
                .params(params)//
                .headers(headers)//
                .build()//
                .execute(new MyStringCallback());
    }


    public void multiFileUpload(View view)
    {
        File file = new File(Environment.getExternalStorageDirectory(), "messenger_01.png");
        File file2 = new File(Environment.getExternalStorageDirectory(), "test1#.txt");
        if (!file.exists())
        {
            Toast.makeText(MainActivity.this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("username", "张鸿洋");
        params.put("password", "123");

        String url = mBaseUrl + "user!uploadFile";
        OkHttpUtils.post()//
                .addFile("mFile", "messenger_01.png", file)//
                .addFile("mFile", "test1.txt", file2)//
                .url(url)
                .params(params)//
                .build()//
                .execute(new MyStringCallback());
    }


    public void downloadFile(View view)
    {
        String url = "https://app.nm139.com:8443/nmydoa/nmydoa.apk";
        OkHttpUtils//
                .get()//
                .url(url)//
                .build()//
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), new File(url).getName())//
                {

                    @Override
                    public void onBefore(Request request, int id)
                    {
                    }

                    @Override
                    public void inProgress(float progress, long total, int id)
                    {
                        mProgressBar.setProgress(Math.round (100 * progress));
                        Log.e(TAG, "inProgress :" + Math.round (100 * progress));
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e, int id)
                    {
                        Log.e(TAG, "onError :" + e.getMessage());
                    }

                    @Override
                    public void onResponse(File file, int id)
                    {
                        Log.e(TAG, "onResponse :" + file.getAbsolutePath());
                    }
                });
    }


    public void otherRequestDemo(View view)
    {
        //also can use delete ,head , patch
        /*
        OkHttpUtils
                .put()//
                .url("http://11111.com")
                .requestBody
                        ("may be something")//
                .build()//
                .execute(new MyStringCallback());



        OkHttpUtils
                .head()//
                .url(url)
                .addParams("name", "zhy")
                .build()
                .execute();

       */


    }

    public void clearSession(View view)
    {
        CookieJar cookieJar = OkHttpUtils.getInstance().getOkHttpClient().cookieJar();
        if (cookieJar instanceof CookieJarImpl)
        {
            ((CookieJarImpl) cookieJar).getCookieStore().removeAll();
        }
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
