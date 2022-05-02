package com.lzy.corebiz

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lzy.corebiz.httpservice.bean.BaseResult
import com.lzy.corebiz.login.UserMgr
import com.lzy.libhttp.exception.HttpExceptionUtil
import com.lzy.libhttp.exception.HttpRequestError
import com.lzy.libhttp.exception.LiveDataHttpErrorHandler
import com.lzy.libhttp.exception.ToastHttpErrorHandler
import com.lzy.libview.BaseViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by zhaoyang.li5 on 2022/4/17 17:39
 */
open class BaseRequestViewModel : BaseViewModel() {
    protected val mHttpRequestErrorLiveData = MutableLiveData<HttpRequestError>()

    fun getHttpRequestErrorLiveData() = mHttpRequestErrorLiveData

    private val mNeedLoginLiveData = MutableLiveData<Boolean>()
    val getNeedLoginLiveData = mNeedLoginLiveData

    protected fun launchLiveDataHandlerRequest(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            HttpExceptionUtil.catchException(
                throwable, LiveDataHttpErrorHandler(getHttpRequestErrorLiveData())
            )
        }) {
            block.invoke(this)
        }
    }

    protected fun launchToastHandlerRequest(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            HttpExceptionUtil.catchException(
                throwable, ToastHttpErrorHandler()
            )
        }) {
            block.invoke(this)
        }
    }

    protected fun <D> handleBaseResult(result: BaseResult<D>): D? {
        var d: D? = null
        when {
            result.success() -> {
                result.data?.let {
                    d = it
                } ?: run {
                    // 返回结果为空
                    mHttpRequestErrorLiveData.value = HttpRequestError.EmptyError
                }
            }
            result.needLogin() -> {
                // 清除本地用户信息
                UserMgr.logOut()
                mNeedLoginLiveData.value = true
            }
            else -> {
                mHttpRequestErrorLiveData.value = HttpRequestError.ServerError(result.errorMsg)
            }
        }
        return d
    }
}