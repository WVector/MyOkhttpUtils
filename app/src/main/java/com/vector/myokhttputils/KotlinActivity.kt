package com.vector.myokhttputils

import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
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
                .addParam("key1", "value1")
                .url("http://www.weather.com.cn/data/sk/101110101.html")//
                .build()//
                .exe<String> {
                    onSucceed { response, id ->
                        mTv?.text = response
                        println(id)
                    }
                }

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
