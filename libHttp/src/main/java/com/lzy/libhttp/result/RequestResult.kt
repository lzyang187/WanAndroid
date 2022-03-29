package com.lzy.libhttp.result

/**
 * @author: zyli44
 * @date: 2022/1/28 16:13
 * @description: 网络请求结果类
 */
sealed class RequestResult<out R> {
    data class Success<out T>(val data: T) : RequestResult<T>()
    data class Error(val exception: Exception) : RequestResult<Nothing>()
}
