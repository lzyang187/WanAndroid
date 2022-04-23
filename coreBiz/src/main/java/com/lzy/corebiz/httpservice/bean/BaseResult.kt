package com.lzy.corebiz.httpservice.bean

import java.io.Serializable

/**
 * Created by zhaoyang.li5 on 2022/4/4 10:05
 */
data class BaseResult<D>(val errorCode: Int, val errorMsg: String, val data: D?) : Serializable {

    fun success() = errorCode == 0

    /**
     * 登录过期
     */
    fun tokenTimeout() = errorCode == -1001
}
