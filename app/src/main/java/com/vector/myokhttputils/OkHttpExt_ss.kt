//package com.vector.myokhttputils
//
//import android.icu.lang.UCharacter.GraphemeClusterBreak.T
//import android.util.Log
//import com.alibaba.fastjson.JSON
//import com.zhy.http.okhttp.callback.Callback
//import com.zhy.http.okhttp.request.RequestCall
//import okhttp3.Call
//import okhttp3.Request
//import okhttp3.Response
//import java.io.IOException
//import java.lang.Exception
//
//
///**
// * Created by Vector
// * on 2017/5/31 0031.
// */
//inline fun <reified T : Any> RequestCall.exe(destFileDir: String = "", destFileName: String = "", noinline callback: KCallback<T>.() -> Unit)
//        = execute(KCallback(destFileDir, destFileDir, T::class.java).apply(callback))
//
//
//class KCallback<T>(val destFileDir: String = "", val destFileName: String = "", val clazz: Class<T>?) : Callback<T>() {
//
//
//    @Throws(IOException::class)
//    override fun parseNetworkResponse(response: Response, id: Int): T {
//        Log.e("", "clazz : " + clazz)
//
//        if (clazz == String::class.java) {
//
//            return response.body().toString() as T
//
//
//        }
//
////        else if (clazz == File::class.java) {
////
////            return saveFile(response, id) as T
////        }
//        else {
//            val string = response.body().string()
//            return JSON.parseObject(string, clazz)
//        }
//
//
////
////
//    }
//
//    private var _onAfter: ((id: Int) -> Unit)? = null
//
//    private var _onBefore: ((request: Request) -> Unit)? = null
//
//    private var _onSucceed: ((obj: T) -> Unit)? = null
//
//    private var _onFailed: ((errorMsg: String) -> Unit)? = null
//
//    override fun onError(call: Call?, response: Response?, e: Exception?, id: Int) {
//        _onFailed?.invoke(validateError(e, response))
//    }
//
//    override fun onResponse(response: T, id: Int) {
//        _onSucceed?.invoke(response)
//    }
//
//
//    override fun onAfter(id: Int) {
//        super.onAfter(id)
//        _onAfter?.invoke(id)
//    }
//
//    override fun onBefore(request: Request?, id: Int) {
//        super.onBefore(request, id)
//        request?.let { _onBefore?.invoke(it) }
//
//    }
//
////    override fun inProgress(progress: Float, total: Long, id: Int) {
////        super.inProgress(progress, total, id)
////    }
//
//    fun onFailed(listener: (errorMsg: String) -> Unit) {
//        _onFailed = listener
//    }
//
//    fun onSucceed(listener: (obj: T) -> Unit) {
//        _onSucceed = listener
//    }
//
//    fun onBefore(listener: (request: Request) -> Unit) {
//        _onBefore = listener
//    }
//
//    fun onAfter(listener: (id: Int) -> Unit) {
//        _onAfter = listener
//    }
//
//}