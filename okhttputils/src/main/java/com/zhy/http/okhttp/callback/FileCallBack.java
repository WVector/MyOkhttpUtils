package com.zhy.http.okhttp.callback;

import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.Response;
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
    /**
     * 目标文件存储的文件夹路径
     */
    private String destFileDir;
    /**
     * 目标文件存储的文件名
     */
    private String destFileName;


    public FileCallBack(String destFileDir, String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
    }


    @Override
    public File parseNetworkResponse(Response response, int id) throws Exception {
        return saveFile(response, id);
    }


    public File saveFile(Response response, final int id) throws IOException {

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

                    final int rate =Math.round(sum *1F/ totalSize * 100F);
                    if (oldRate != rate) {
                        OkHttpUtils.getInstance().getDelivery().execute(new Runnable() {
                            @Override
                            public void run() {
                                inProgress(rate * 0.01F, totalSize, id);
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


}
