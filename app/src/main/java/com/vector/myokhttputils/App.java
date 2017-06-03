package com.vector.myokhttputils;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.HttpParams;

/**
 * Created by Vector
 * on 2017/2/6 0006.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        HttpParams httpParams = new HttpParams();
        httpParams.addParam("key1", "value1");

        OkHttpUtils.getInstance().init(this)
                .debug(true, "okhttp")
                .timeout(20 * 1000L)
                .setGlobalParams(new MyGlobalParams());
    }
}
