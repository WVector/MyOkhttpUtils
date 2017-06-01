package com.zhy.http.okhttp.builder;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.request.PostFileRequest;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;

/**
 * Created by zhy on 15/12/14.
 */
public class PostFileBuilder extends OkHttpRequestBuilder<PostFileBuilder> {
    private File file;
    private MediaType mediaType;


    public OkHttpRequestBuilder file(File file) {
        this.file = file;
        return this;
    }

    public OkHttpRequestBuilder mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build() {
        //添加全局参数
        Map<String, String> map = OkHttpUtils.getInstance().getHttpParams().getParams();
        if (this.params != null) {
            this.params.putAll(map);
        } else {
            this.params = map;
        }
        return new PostFileRequest(url, tag, this.params, headers, file, mediaType, id).build();
    }


}
