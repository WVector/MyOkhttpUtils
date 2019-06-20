package com.vector.myokhttputils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by Vector
 * on 2019-06-20.
 */
public class Inter1 implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        System.out.println("1 b");
        Response proceed = chain.proceed(chain.request());
        System.out.println("1 a");
        return proceed;
    }
}
