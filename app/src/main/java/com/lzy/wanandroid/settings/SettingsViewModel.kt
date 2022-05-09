package com.lzy.wanandroid.settings

import androidx.lifecycle.MutableLiveData
import com.lzy.corebiz.BaseRequestViewModel
import com.lzy.corebiz.login.UserMgr
import com.lzy.corebiz.login.ui.login.LoginRepository
import com.lzy.libhttp.exception.HttpRequestError
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job

/**
 * Created by zhaoyang.li5 on 2022/5/9 9:29
 */
@HiltViewModel
class SettingsViewModel @Inject constructor() : BaseRequestViewModel() {

    private var mLogoutJob: Job? = null
    private val mLogoutLiveData = MutableLiveData<Boolean>()
    val getLogoutLiveData = mLogoutLiveData

    private val mLoginRepository by lazy {
        LoginRepository()
    }

    fun logout() {
        mLogoutJob = launchLiveDataHandlerRequest {
            val result = mLoginRepository.logout()
            when {
                result.success() -> {
                    UserMgr.logOut()
                    mLogoutLiveData.value = true
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
        }
    }

    fun cancelLogin() {
        mLogoutJob?.cancel()
    }
}