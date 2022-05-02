package com.lzy.wanandroid

import android.app.Application
import com.blankj.utilcode.util.LogUtils
import com.lzy.corebiz.AppConfig
import com.lzy.corebiz.login.UserMgr
import com.lzy.libhttp.RetrofitBuildHelper
import com.lzy.libview.LibViewInit
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by zhaoyang.li5 on 2022/4/4 8:33
 */
@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppConfig.init(
            this,
            BuildConfig.DEBUG,
            BuildConfig.VERSION_CODE,
            BuildConfig.VERSION_NAME,
            getString(R.string.app_name),
            BuildConfig.APPLICATION_ID
        )
        LogUtils.getConfig().setLogSwitch(BuildConfig.DEBUG)
            .setBorderSwitch(false).isLogHeadSwitch = false
        UserMgr.init()
        RetrofitBuildHelper.init("https://www.wanandroid.com")
        LibViewInit.initRefresh(this)
    }

}