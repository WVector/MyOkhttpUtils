package com.vector.myokhttputils;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.OuterCallback;
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

        @Override
        public Context getContext() {
            return MainActivity.this;
        }
    }

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
            return;
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
        OkHttpUtils.get().url("http://www.baidu.com").build().execute(new MyStringCallback());
    }


    public void getUser(View view) {
        String url = mBaseUrl + "getuser";
        OkHttpUtils
                .post()//
                .url(url)//
                .addParams("username", "hyman")//
                .addParams("password", "123")//
                .build()//
                .execute(new UserCallback());
    }

    class UserCallback extends OuterCallback<User> {
        @Override
        public Context getContext() {
            return MainActivity.this;
        }

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
