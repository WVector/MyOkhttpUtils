package com.vector.myokhttputils.ui;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vector.myokhttputils.R;
import com.vector.myokhttputils.bean.User;
import com.vector.myokhttputils.bean.Weather;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.SimpleCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private String mBaseUrl = "http://10.222.5.93/web/";
    private TextView mTv;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv = (TextView) findViewById(R.id.id_textview);
        mProgressBar = (ProgressBar) findViewById(R.id.id_progress);
        mProgressBar.setMax(100);
    }

    public void uploadFile(View view) {

        File file = new File(Environment.getExternalStorageDirectory(), "aa.apk");
        if (!file.exists()) {
            Toast.makeText(MainActivity.this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
//            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("account", "张三");
        params.put("password", "123456");
//
//        Map<String, String> headers = new HashMap<>();
//        headers.put("APP-Key", "APP-Secret222");
//        headers.put("APP-Secret", "APP-Secret111");

        String url = mBaseUrl + "UploadServlet";

        OkHttpUtils.post()//
                .addFile("file", "aa.apk", file)//
                .url(url)//
                .params(params)//
//                .headers(headers)//
                .build()//
                .execute(new MyStringCallback());
    }

    public void getHtml(View view) {
//        String url = "https://kyfw.12306.cn/otn/";
        String url = "https://app.nm139.com:8443/nmydoa/nmydoa.html";
//        String url = "https://app.nm139.com:8443/nmydoa/nmydoa.html";
//        OkHttpUtils.get().url(url1).build().execute(new MyStringCallback());
        OkHttpUtils.get().url(url).build().execute(new MyStringCallback());

//        OkGo.<String>get(url).execute(new com.lzy.okgo.callback.StringCallback() {
//            @Override
//            public void onSuccess(com.lzy.okgo.model.Response<String> response) {
//                mTv.setText(response.body());
//            }
//
//            @Override
//            public void onError(com.lzy.okgo.model.Response<String> response) {
//                mTv.setText(response.body());
//            }
//        });
    }

    public void getUser(View view) {
        String url = mBaseUrl + "getuser";
        OkHttpUtils
                .post()//
                .url(url)//
                .addParam("username", "hyman")//
                .addParam("password", "123")//
                .build()//
                .execute(new UserCallback());
    }

    public void getWeather(View view) {
        OkHttpUtils
                .get()//
                .url("http://www.weather.com.cn/data/sk/101110101.html")//
                .build()//
                .execute(new SimpleCallback<Weather>() {
                    @Override
                    public void onError(Call call, Response response, Exception e, int id) {
                        mTv.setText(validateError(e, response));
                    }

                    @Override
                    public void onResponse(Weather response, int id) {
                        mTv.setText(response.toString());
                    }
                });
    }

    public void downloadApp(View view) {

        String url = "https://quasi.nm139.com:8442/waptemp/f1692dc5-747f-4aed-b0c3-7a11fc873c94.rar";
        String fileName = "关于表彰全区优秀质量管理小组、质量管理小组活动优秀企业、质量管理小组活动优秀组织单位、质量管理小组活动卓越领导者和优秀推进者及全区质量信得过班组、质量信得过班组建设优秀企业和质量信得过班组建设先进个人的决定.rar";


        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
//
//        String filePath = path.concat(File.separator).concat(fileName);
//
//        File file = new File(filePath);
//
//        try {
//            if (file.createNewFile()) {
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        if (file.exists()) {
//            System.out.println(file.getAbsolutePath());
//        } else {
//            System.out.println("不存在");
//        }


        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new FileCallBack(path, fileName) {
                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                        Log.d(TAG, "inProgress() called with: progress = [" + progress + "], total = [" + total + "], id = [" + id + "]");
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e, int id) {
                        Log.d(TAG, validateError(e, response));
                    }

                    @Override
                    public void onResponse(File response, int id) {
                        Log.d(TAG, response.getAbsolutePath());

                    }

                    @Override
                    public void onBefore(Request request, int id) {
                        super.onBefore(request, id);
                        Log.d(TAG, "onBefore() called with: request = [" + request + "], id = [" + id + "]");
                    }
                });

    }

    public class MyStringCallback extends StringCallback {

        @Override
        public void onBefore(Request request, int id) {
            setTitle("loading...");
        }

        @Override
        public void onAfter(int id) {
            setTitle("Sample-okHttp");
        }

        @Override
        public void onError(Call call, Response response, Exception e, int id) {
            e.printStackTrace();
            String errorMessage = validateError(e, response);
            mTv.setText("onError:" + errorMessage);
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e(TAG, "onResponse：complete");
            mTv.setText("onResponse:" + response);

            switch (id) {
                case 100:
                    Toast.makeText(MainActivity.this, "http", Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    Toast.makeText(MainActivity.this, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            Log.e(TAG, "inProgress:" + progress);
            mProgressBar.setProgress((int) (100 * progress));
        }

    }

    class UserCallback extends SimpleCallback<User> {

        @Override
        public void onError(Call call, Response response, Exception e, int id) {
            mTv.setText(validateError(e, response));
        }

        @Override
        public void onResponse(User response, int id) {
            mTv.setText(response.toString());
        }
    }

}
