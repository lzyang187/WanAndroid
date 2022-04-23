package com.lzy.libhttp.exception

/**
 * Created by zhaoyang.li5 on 2022/4/17 16:30
 */
interface HttpErrorHandler {
    fun onError(httpRequestError: HttpRequestError)
}