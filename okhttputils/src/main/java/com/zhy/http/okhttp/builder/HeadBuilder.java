package com.zhy.http.okhttp.builder;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.request.OtherRequest;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.Map;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder {
    @Override
    public RequestCall build() {
        //添加全局参数
        Map<String, String> map = OkHttpUtils.getInstance().getHttpParams().getParams();
        if (this.params != null) {
            this.params.putAll(map);
        } else {
            this.params = map;
        }
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers, id).build();
    }
}
