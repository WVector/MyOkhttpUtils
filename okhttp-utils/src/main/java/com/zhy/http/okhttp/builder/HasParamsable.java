package com.zhy.http.okhttp.builder;

import java.util.Map;

/**
 * Created by zhy on 16/3/1.
 */
public interface HasParamsable
{
    OkHttpRequestBuilder params(Map<String, String> params);

    OkHttpRequestBuilder addParam(String key, String val);
}
