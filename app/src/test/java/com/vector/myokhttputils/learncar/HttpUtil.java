package com.vector.myokhttputils.learncar;

import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;

import okhttp3.MediaType;

/**
 * Created by Vector
 * on 2017/7/12 0012.
 */

public class HttpUtil {
    public static String post(String url, String json) {
        try {
            return OkHttpUtils
                    .postString()
                    .url(url)
                    .addHeader("User-Agent", "Dalvik/2.1.0 (Linux; U; Android 7.0; MI 5 MIUI/7.7.6)")
                    .content(json)
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .build()
                    .execute()
                    .body()
                    .string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
