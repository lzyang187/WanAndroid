package com.lzy.corebiz.login.ui.login

import com.lzy.corebiz.httpservice.WanAndroidHttpService
import com.lzy.corebiz.httpservice.bean.BaseResult
import com.lzy.corebiz.httpservice.bean.UserBean
import com.lzy.libhttp.RetrofitBuildHelper

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository() {

    suspend fun login(username: String, password: String): BaseResult<UserBean> {
        val httpService = RetrofitBuildHelper.create(WanAndroidHttpService::class.java)
        return httpService.login(username, password)
    }

    suspend fun logout() {

    }


}