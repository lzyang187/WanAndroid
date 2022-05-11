package com.lzy.libhttp

import android.content.Context
import android.util.Log
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor

/**
 * Created by zhaoyang.li5 on 2022/5/11 13:51
 */
object CookieUtil {
    private lateinit var mPersistentCookieJar: PersistentCookieJar
    fun getPersistentCookieJar() = mPersistentCookieJar

    fun init(context: Context) {
        mPersistentCookieJar = PersistentCookieJar(
            SetCookieCache(), SharedPrefsCookiePersistor(context.applicationContext)
        )
    }
}