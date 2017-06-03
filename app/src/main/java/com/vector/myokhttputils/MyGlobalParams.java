package com.vector.myokhttputils;

import android.util.Log;

import com.zhy.http.okhttp.builder.GlobalParams;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Vector
 * on 2017/6/4 0004.
 */

public class MyGlobalParams implements GlobalParams {
    private static final String TAG = MyGlobalParams.class.getSimpleName();

    @Override
    public Map<String, String> addParams() {
        Log.d(TAG, "addParams() called");
        Map<String, String> map = new LinkedHashMap<>();


        map.put("key1", "value1");
        return map;
    }
}
