package com.vector.myokhttputils;

import com.zhy.http.okhttp.OkHttpUtils;

import org.junit.Test;

import java.io.IOException;

/**
 * Created by Vector
 * on 2019-06-20.
 */
public class InterceptorTest {
    @Test
    public void test() {
        OkHttpUtils.getInstance()
                .addNetworkInterceptor(new Inter1())
                .addNetworkInterceptor(new Inter2())
                .addInterceptor(new Inter3())
                .addInterceptor(new Inter4());

        try {
            String string = OkHttpUtils.get().url("https://www.baidu.com").build().execute().body().string();
            System.out.println(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
