package com.zhy.http.okhttp.callback;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.utils.Utils;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by Vector
 * on 2017/2/14 0014.
 */

public abstract class SimpleCallback<T> extends Callback<T> {
//    private Class<T> mClazz;
//
//    public InnerCallback(Class<T> clazz) {
//        mClazz = clazz;
//    }

    @Override
    public T parseNetworkResponse(Response response, int id) throws IOException {
        String string = response.body().string();
        return JSON.parseObject(string, Utils.findNeedType(getClass()));
    }
}
