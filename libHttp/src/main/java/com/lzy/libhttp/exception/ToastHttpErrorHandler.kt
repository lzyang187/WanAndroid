package com.lzy.libhttp.exception

import com.blankj.utilcode.util.ToastUtils
import com.lzy.libhttp.R

/**
 * Created by zhaoyang.li5 on 2022/4/17 16:32
 */
class ToastHttpErrorHandler : HttpErrorHandler {
    override fun onError(httpRequestError: HttpRequestError) {
        when (httpRequestError) {
            is HttpRequestError.NetworkError -> ToastUtils.showShort(R.string.lib_http_network_error)
            is HttpRequestError.TimeoutError -> ToastUtils.showShort(R.string.lib_http_network_timeout)
            is HttpRequestError.ServerError -> ToastUtils.showShort(R.string.lib_http_server_error)
            is HttpRequestError.EmptyError -> ToastUtils.showShort(R.string.lib_http_result_empty)
        }
    }
}