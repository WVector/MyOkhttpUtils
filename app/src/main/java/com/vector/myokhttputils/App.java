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
                .debug(BuildConfig.DEBUG, "okhttp")
                .timeout(40 * 1000L)
                .sslSocketFactory(null, null, null);
    }
}
