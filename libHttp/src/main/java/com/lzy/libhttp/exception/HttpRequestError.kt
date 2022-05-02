package com.lzy.libhttp.exception

/**
 * @author: zyli44
 * @date: 2022/1/28 16:13
 * @description: 网络请求异常类
 */
sealed class HttpRequestError {
    object NetworkError : HttpRequestError()
    object TimeoutError : HttpRequestError()
    class ServerError(val errorMsg: String? = null) : HttpRequestError()
    object EmptyError : HttpRequestError()
}
