package com.lzy.corebiz

import android.app.Application

/**
 * 全局配置信息
 * @author: cyli8
 * @date: 2019/2/12 15:06
 */
object AppConfig {
    lateinit var APPLICATION: Application
    var DEBUG: Boolean = false
    var VERSION_CODE: Int = 0
    lateinit var VERSION_NAME: String
    lateinit var APP_NAME: String
    lateinit var PACKAGE_NAME: String


    fun init(
        application: Application,
        debug: Boolean,
        versionCode: Int,
        versionName: String,
        appName: String,
        packageName: String
    ) {
        APPLICATION = application
        DEBUG = debug
        VERSION_CODE = versionCode
        VERSION_NAME = versionName
        APP_NAME = appName
        PACKAGE_NAME = packageName
    }
}