package com.zhy.http.okhttp.callback;

import android.text.TextUtils;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.utils.Utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

/**
 * Created by zhy on 15/12/15.
 */
public abstract class FileCallBack extends Callback<File> {

    private static String APK_CONTENTTYPE = "application/vnd.android.package-archive";
    private static String PNG_CONTENTTYPE = "image/png";
    private static String JPG_CONTENTTYPE = "image/jpg";
//    private static String TEXT_CONTENTTYPE = "text/html; charset=utf-8";
//    private static String fileSuffix = "";
    /**
     * 目标文件存储的文件夹路径
     */
    private String destFileDir;
    /**
     * 目标文件存储的文件名
     */
    private String destFileName;


    public FileCallBack(String destFileDir, @Nullable String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
    }


    @Override
    public File parseNetworkResponse(Response response, int id) throws Exception {
        return saveFile(response, id);
    }


    public File saveFile(Response response, final int id) throws IOException {

        //生成文件名称

        destFileName = verifyFileName(this.destFileDir, this.destFileName, response);


        //生成存放文件的file
        File dir = new File(destFileDir);
        if (!dir.exists()) {
            boolean mkdirs = dir.mkdirs();
            if (!mkdirs) {
                //不能创建文件
            }
        }
        File file = new File(dir, destFileName);

        //输出流
        Sink sink = Okio.sink(file);
        //输入流
        Source source = Okio.source(response.body().byteStream());
        //文件总大小
        final long totalSize = response.body().contentLength();
        //写入到本地存储空间中
        BufferedSink bufferedSink = Okio.buffer(sink);

        //写出，并且使用代理监听写出的进度。回调UI线程的接口
        bufferedSink.writeAll(new ForwardingSource(source) {
            long sum = 0;
            int oldRate = 0;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long readSize = super.read(sink, byteCount);
                if (readSize != -1L) {
                    sum += readSize;

                    final int rate = Math.round(sum * 1F / totalSize * 100F);
                    if (oldRate != rate) {
                        OkHttpUtils.getInstance().getDelivery().execute(new Runnable() {
                            @Override
                            public void run() {
                                inProgress(rate * 1F / 100, totalSize, id);
                            }
                        });
                        oldRate = rate;
                    }

                }
                return readSize;
            }
        });


        //刷新数据
        bufferedSink.flush();

        //关流
        Util.closeQuietly(sink);

        //关流
        Util.closeQuietly(source);

        return file;

    }

    private String verifyFileName(String destFileDir, @Nullable String name, @NotNull Response response) {
        ResponseBody body = response.body();
        //不为空
        if (!TextUtils.isEmpty(name)) {
            name = getRealFileName(name, body);
        } else {
            //根据其他条件，生成文件全名
            name = createFileName(response);
        }

        name = verifyFileNameTooLong(destFileDir, name, response);

        return name;
    }

    private String verifyFileNameTooLong(String destFileDir, @Nullable String name, Response response) {
        //防止文件名称命名过长，造成异常
        File file = new File(destFileDir.concat(File.separator).concat(name));
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            name = createFileName(response);
            //防止文件名称命名过长，造成异常
            File file1 = new File(destFileDir.concat(File.separator).concat(name));
            try {
                file1.createNewFile();
            } catch (IOException ee) {
                ee.printStackTrace();
                name = getRandomFileName(response.body());
            }
        }
        return name;
    }

    private String getRealFileName(@NotNull String name, @NotNull ResponseBody body) {
        if (!name.contains(".")) {
            name = name + getFileRealSuffix(body);
        }
        return name;
    }

    private String getFileRealSuffix(@NotNull ResponseBody body) {
        String fileSuffix = "";
        if (body.contentType() != null) {
            String type = body.contentType().toString();
            if (type.equals(APK_CONTENTTYPE)) {
                fileSuffix = ".apk";
            } else if (type.equals(PNG_CONTENTTYPE)) {
                fileSuffix = ".png";
            } else if (type.equals(JPG_CONTENTTYPE)) {
                fileSuffix = ".jpg";
            } else {
                fileSuffix = "." + body.contentType().subtype();
            }
        }
        return fileSuffix;
    }


    private String createFileName(@NotNull Response response) {
        ResponseBody body = response.body();
        String name;
        try {
            String url = response.newBuilder().build().request().url().toString();
            name = Utils.getFileName(url);
            //不为空
            if (!TextUtils.isEmpty(name)) {
                name = getRealFileName(name, body);
            } else {
                //根据其他条件，生成文件全名
                name = getRandomFileName(body);
            }

        } catch (Exception e) {
            e.printStackTrace();
            name = getRandomFileName(body);
        }
        return name;
    }

    private String getRandomFileName(@NotNull ResponseBody responseBody) {
        return System.currentTimeMillis() + getFileRealSuffix(responseBody);
    }


}
