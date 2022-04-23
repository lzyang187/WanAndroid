package com.lzy.libhttp.exception

import android.accounts.NetworkErrorException
import com.google.gson.JsonSyntaxException
import com.google.gson.stream.MalformedJsonException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import retrofit2.HttpException

/**
 * Created by zhaoyang.li5 on 2022/4/17 15:49
 */
object HttpExceptionUtil {

    fun catchException(
        throwable: Throwable, errorHandler: HttpErrorHandler?
    ) {
        when (throwable) {
            is HttpException -> {
                if (throwable.code() in 200 until 300) {
                    return
                } else {
                    errorHandler?.onError(HttpRequestError.ServerError)
                }
            }
            is SocketTimeoutException -> {
                errorHandler?.onError(HttpRequestError.TimeoutError)
            }
            is UnknownHostException, is NetworkErrorException -> {
                errorHandler?.onError(HttpRequestError.NetworkError)
            }
            is ConnectException, is MalformedJsonException, is JsonSyntaxException, is InterruptedIOException -> {
                errorHandler?.onError(HttpRequestError.ServerError)
            }
            else -> {
                errorHandler?.onError(HttpRequestError.ServerError)
            }
        }
    }

}