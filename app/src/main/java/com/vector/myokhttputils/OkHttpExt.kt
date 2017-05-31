package com.vector.myokhttputils

import android.util.Log
import com.alibaba.fastjson.JSON
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.Callback
import com.zhy.http.okhttp.request.RequestCall
import okhttp3.Call
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.Util
import okio.Buffer
import okio.ForwardingSource
import okio.Okio
import java.io.File
import java.io.IOException
import java.lang.Exception


/**
 * Created by Vector
 * on 2017/5/31 0031.
 */
inline fun <reified T : Any> RequestCall.exe(destFileDir: String = "", destFileName: String = "", noinline callback: KCallback<T>.() -> Unit)
        = execute(KCallback(destFileDir, destFileName, T::class.java).apply(callback))


@Suppress("UNCHECKED_CAST")
class KCallback<T>(val destFileDir: String = "", val destFileName: String = "", val clazz: Class<T>?) : Callback<T>() {


    @Throws(IOException::class)
    override fun parseNetworkResponse(response: Response, id: Int): T {
        if (clazz == String::class.java) {
            return response.body().string() as T
        } else if (clazz == File::class.java) {
            return saveFile(response, id) as T
        } else {
            val string = response.body().string()
            return JSON.parseObject(string, clazz)
        }
    }

    private var _onStart: ((request: Request, id: Int) -> Unit)? = null

    private var _onProgress: ((progress: Float, total: Long, id: Int) -> Unit)? = null

    private var _onFailed: ((errorMsg: String, e: Exception?, id: Int) -> Unit)? = null

    private var _onSucceed: ((response: T, id: Int) -> Unit)? = null

    private var _onEnd: ((id: Int) -> Unit)? = null

    /**
     * 进度条，下载文件
     */
    override fun inProgress(progress: Float, total: Long, id: Int) {
        _onProgress?.invoke(progress, total, id)
    }

    /**
     * 异常处理
     */
    override fun onError(call: Call?, response: Response?, e: Exception?, id: Int) {
        _onFailed?.invoke(validateError(e, response), e, id)
    }


    /**
     * 返回值处理
     */
    override fun onResponse(response: T, id: Int) {
        _onSucceed?.invoke(response, id)
    }

    /**
     * 结束回调
     */
    override fun onAfter(id: Int) {
        _onEnd?.invoke(id)
    }

    /**
     * 开始回调
     */
    override fun onBefore(request: Request?, id: Int) {
        request?.let { _onStart?.invoke(it, id) }
    }

    /**
     * 失败回调
     */
    fun onFailed(listener: (errorMsg: String, e: Exception?, id: Int) -> Unit) {
        _onFailed = listener
    }

    /**
     * 成功回调
     */
    fun onSucceed(listener: (response: T, id: Int) -> Unit) {
        _onSucceed = listener
    }

    /**
     * 开始回调
     */
    fun onStart(listener: (request: Request, id: Int) -> Unit) {
        _onStart = listener
    }

    /**
     * 结束回调
     */
    fun onEnd(listener: (id: Int) -> Unit) {
        _onEnd = listener
    }

    /**
     * 进度回调
     */
    fun onProgress(listener: (progress: Float, total: Long, id: Int) -> Unit) {
        _onProgress = listener

    }


    @Throws(IOException::class)
    private fun saveFile(response: Response, id: Int): File {
        //生成存放文件的file
        val dir = File(destFileDir)
        if (!dir.exists()) {
            val mkdirs = dir.mkdirs()
            if (!mkdirs) {
                //不能创建文件
                Log.e("okHttp", "不能创建文件")
            }
        }
        val file = File(dir, destFileName)
        //输出流
        val sink = Okio.sink(file)
        //输入流
        val source = Okio.source(response.body().byteStream())
        //文件总大小
        val totalSize = response.body().contentLength()
        //写入到本地存储空间中
        val bufferedSink = Okio.buffer(sink)

        //写出，并且使用代理监听写出的进度。回调UI线程的接口
        bufferedSink.writeAll(object : ForwardingSource(source) {
            internal var sum: Long = 0
            internal var oldRate = 0

            @Throws(IOException::class)
            override fun read(sink: Buffer, byteCount: Long): Long {
                val readSize = super.read(sink, byteCount)
                if (readSize != -1L) {
                    sum += readSize

                    val rate = Math.round(sum * 1f / totalSize * 100f)
                    if (oldRate != rate) {
                        OkHttpUtils.getInstance().delivery.execute { inProgress(rate * 0.01f, totalSize, id) }
                        oldRate = rate
                    }

                }
                return readSize
            }
        })


        //刷新数据
        bufferedSink.flush()

        //关流
        Util.closeQuietly(sink)

        //关流
        Util.closeQuietly(source)

        return file

    }

}