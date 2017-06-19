package com.vector.myokhttputils

import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.view.View
import android.widget.TextView
import com.alibaba.fastjson.JSON
import com.zhy.http.okhttp.OkHttpUtils
import kotlinx.android.synthetic.main.activity_kotlin.*
import java.io.File


class KotlinActivity : AppCompatActivity() {

    private var mTv: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        mTv = findViewById(R.id.tv_result) as TextView

    }

    fun goDead(view: View) {
        OkHttpUtils.get()
                .url("https://api.github.com/repos/CXZYH/GoDead/readme")
                .build()
                .exe<String> {
                    onSucceed { response, _ ->
                        print(response)
                        val jsonObject = JSON.parseObject(response)
                        val name = jsonObject.getString("content")
                        val decode = Base64.decode(name.toByteArray(), 0)
                        print(String(decode))
                    }
                }

    }

    fun getWeather(view: View) {
//        OkHttpUtils
//                .get()//
//                .url("http://www.weather.com.cn/data/sk/101110101.html")//
//                .build()//
//                .execute(object : InnerCallback<Weather>(Weather::class.java) {
//                    override fun onError(call: Call?, response: Response?, e: Exception?, id: Int) {
//                        mTv?.text = validateError(e, response)
//                    }
//
//                    override fun onResponse(response: Weather?, id: Int) {
//
//                        mTv?.text = response.toString()
//                    }
//
//                })


        OkHttpUtils
                .post()//
                .tag(this@KotlinActivity)
                .addParam("key1", "+ .>")
                .addParam("key2", "测试测试")
                .url("http://10.222.5.93:8080/users/test")//
                .build()//
                .exe<String> {
                    onStart { request, id ->

                    }
                    onEnd {

                    }
                    onSucceed { response, _ ->
                        mTv?.text = response
                        println(response)
                    }

                    onFailed { errorMsg, _, _ ->
                        println(errorMsg)
                        mTv?.text = errorMsg
                    }
                }

    }

    fun print(str: String) {
        tv_result?.text = str
    }

    fun getFile(view: View) {

//        https@ //app.nm139.com:8443/nmydoa/nmydoa.apk

        OkHttpUtils
                .get()//
                .tag(this@KotlinActivity)
                .url("https://app.nm139.com:8443/nmydoa/nmydoa.apk")//
                .build()//
                .exe<File>(Environment.getExternalStorageDirectory().absolutePath, "nmydoa.apk") {

                    onProgress { progress, total, id ->
                        pb?.progress = progress.toInt()
                        println(progress)
                    }

                    onEnd {
                        println("------------------------------下载完成")
                    }

                    onStart { request, id ->
                        println("------------------------------开始下载")
                    }


                    onSucceed { response, id ->
                        println(response.absoluteFile)
                        tv_result?.text = response.absoluteFile.absolutePath
                    }

                    onFailed { errorMsg, e, id ->

                        tv_result?.text = errorMsg
                    }

                }


    }
}
