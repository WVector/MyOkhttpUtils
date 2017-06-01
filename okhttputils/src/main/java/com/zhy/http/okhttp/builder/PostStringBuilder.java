package com.zhy.http.okhttp.builder;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.request.PostStringRequest;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.Map;

import okhttp3.MediaType;

/**
 * Created by zhy on 15/12/14.
 */
public class PostStringBuilder extends OkHttpRequestBuilder<PostStringBuilder>
{
    private String content;
    private MediaType mediaType;


    public PostStringBuilder content(String content)
    {
        this.content = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType)
    {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build()
    {

        //添加全局参数
        Map<String, String> map = OkHttpUtils.getInstance().getHttpParams().getParams();
        if (this.params != null) {
            this.params.putAll(map);
        } else {
            this.params = map;
        }

        return new PostStringRequest(url, tag, params, headers, content, mediaType,id).build();
    }


}
