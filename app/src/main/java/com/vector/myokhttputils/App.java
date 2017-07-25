package com.vector.myokhttputils;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;

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
                .timeout(6 * 1000L)
                .setGlobalParams(new MyGlobalParams());


    }
}
