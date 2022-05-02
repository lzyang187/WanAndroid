package com.lzy.corebiz.login.register

import com.lzy.corebiz.httpservice.WanAndroidHttpService
import com.lzy.corebiz.httpservice.bean.BaseResult
import com.lzy.corebiz.httpservice.bean.UserBean
import com.lzy.libhttp.RetrofitBuildHelper

/**
 * Created by zhaoyang.li5 on 2022/5/2 14:33
 */
class RegisterRepository {

    suspend fun register(
        username: String, password: String, repassword: String
    ): BaseResult<UserBean> {
        val httpService = RetrofitBuildHelper.create(WanAndroidHttpService::class.java)
        return httpService.register(username, password, repassword)
    }

}