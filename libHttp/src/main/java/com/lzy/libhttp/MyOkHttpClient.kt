package com.lzy.libhttp

import java.io.File
import java.util.concurrent.TimeUnit
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

/**
 * @author: cyli8
 * @date: 2019-11-06 15:33
 */
object MyOkHttpClient {
    private val mOkHttpClient: OkHttpClient
    private var mCacheFile: File? = null

    fun setCacheFile(file: File) {
        mCacheFile = file
    }

    fun getOkHttpClient() = mOkHttpClient

    init {
        val newBuilder = OkHttpClient().newBuilder()
        newBuilder.connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS).retryOnConnectionFailure(true)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        mCacheFile?.let {
            val cache = Cache(it, 10 * 1024 * 1024L)
            newBuilder.cache(cache)
        }
        mOkHttpClient = newBuilder.build()
    }
}