package com.lzy.libhttp

import com.blankj.utilcode.util.Utils
import java.io.File
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author: cyli8
 * @date: 2019-11-07 08:55
 */
object RetrofitBuildHelper {

    private lateinit var mRetrofit: Retrofit

    fun init(baseUrl: String) {
        MyOkHttpClient.setCacheFile(File(Utils.getApp().externalCacheDir, "okhttp"))
        mRetrofit = Retrofit.Builder().baseUrl(baseUrl).client(MyOkHttpClient.getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    fun <T> create(serviceClass: Class<T>): T = mRetrofit.create(serviceClass)

}