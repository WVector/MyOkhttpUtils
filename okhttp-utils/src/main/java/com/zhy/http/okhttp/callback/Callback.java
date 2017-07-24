package com.zhy.http.okhttp.callback;

import com.alibaba.fastjson.JSONException;
import com.zhy.http.okhttp.intercepter.NoNetWorkException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public abstract class Callback<T> {
    public static Callback CALLBACK_DEFAULT = new Callback() {


        @Override
        public Object parseNetworkResponse(Response response, int id) throws Exception {
            return null;
        }

        @Override
        public void onError(Call call, Response response, Exception e, int id) {

        }

        @Override
        public void onResponse(Object response, int id) {

        }
    };

    /**
     * UI Thread
     *
     * @param request
     */
    public void onBefore(Request request, int id) {
    }

    /**
     * UI Thread
     *
     * @param id
     */
    public void onAfter(int id) {
    }

    /**
     * UI Thread
     *
     * @param progress
     */
    public void inProgress(float progress, long total, int id) {

    }

    protected String validateError(Exception error, Response response) {

        if (error != null) {
            if (error instanceof NoNetWorkException) {
                return "无网络，请联网重试";
            } else if (error instanceof SocketTimeoutException) {
                return "网络连接超时，请稍候重试";
            } else if (error instanceof JSONException) {
                return "json转化异常";
            } else if (error instanceof ConnectException) {
                return "服务器网络异常或宕机，请稍候重试";
            }
        }


        if (response != null) {
            int code = response.code();
            if (code >= 500) {
                return "服务器异常，请稍候重试";
            } else if (code < 500 && code >= 400) {
                return "接口异常，请稍候重试";
            } else {
                return String.format("未知异常 code = %d，请稍候重试", code);
            }
        }


        return "未知异常，请稍候重试";

    }

    /**
     * if you parse reponse code in parseNetworkResponse, you should make this method return true.
     *
     * @param response
     * @return
     */
    public boolean validateResponse(Response response, int id) {
        return response.isSuccessful();
    }

    /**
     * Thread Pool Thread
     *
     * @param response
     */
    public abstract T parseNetworkResponse(Response response, int id) throws Exception;

    public abstract void onError(Call call, Response response, Exception e, int id);

    public abstract void onResponse(T response, int id);

}