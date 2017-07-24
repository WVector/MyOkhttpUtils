package com.zhy.http.okhttp.builder;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Vector
 * on 2017/6/1 0001.
 */

/**
 * 全局参数
 */
public class HttpParams {
    private Map<String, String> params;

    public HttpParams() {
        params = new LinkedHashMap<String, String>();

    }

    public Map<String, String> getParams() {
        return params;
    }

    public HttpParams params(Map<String, String> params) {
        this.params.putAll(params);
        return this;
    }

    public HttpParams addParam(String key, String val) {
        this.params.put(key, val);
        return this;

    }
}
