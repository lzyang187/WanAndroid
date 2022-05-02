package com.lzy.corebiz.httpservice.bean

import java.io.Serializable

/**
 * Created by zhaoyang.li5 on 2022/4/4 10:05
 */
data class BaseResult<D>(val errorCode: Int, val errorMsg: String, val data: D?) : Serializable {

    fun success() = errorCode == 0

    /**
     * 登录失效，需要重新登录
     */
    fun needLogin() = errorCode == -1001
}
