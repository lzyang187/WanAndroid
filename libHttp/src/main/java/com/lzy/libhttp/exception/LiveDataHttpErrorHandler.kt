package com.lzy.libhttp.exception

import androidx.lifecycle.MutableLiveData

/**
 * Created by zhaoyang.li5 on 2022/4/17 18:07
 */
class LiveDataHttpErrorHandler(private val liveData: MutableLiveData<HttpRequestError>) :
    HttpErrorHandler {
    override fun onError(httpRequestError: HttpRequestError) {
        liveData.value = httpRequestError
    }
}