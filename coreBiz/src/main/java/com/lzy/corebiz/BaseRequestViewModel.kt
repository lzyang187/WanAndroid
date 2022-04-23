package com.lzy.corebiz

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lzy.libhttp.exception.HttpExceptionUtil
import com.lzy.libhttp.exception.HttpRequestError
import com.lzy.libhttp.exception.LiveDataHttpErrorHandler
import com.lzy.libhttp.exception.ToastHttpErrorHandler
import com.lzy.libview.BaseViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Created by zhaoyang.li5 on 2022/4/17 17:39
 */
open class BaseRequestViewModel : BaseViewModel() {
    protected val mHttpRequestErrorLiveData = MutableLiveData<HttpRequestError>()

    fun getHttpRequestErrorLiveData() = mHttpRequestErrorLiveData

    protected fun launchLiveDataHandlerRequest(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            HttpExceptionUtil.catchException(
                throwable, LiveDataHttpErrorHandler(getHttpRequestErrorLiveData())
            )
        }) {
            block.invoke(this)
        }
    }

    protected fun launchToastHandlerRequest(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            HttpExceptionUtil.catchException(
                throwable, ToastHttpErrorHandler()
            )
        }) {
            block.invoke(this)
        }
    }
}