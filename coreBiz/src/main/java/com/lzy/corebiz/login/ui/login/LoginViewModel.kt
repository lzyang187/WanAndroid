package com.lzy.corebiz.login.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lzy.corebiz.BaseRequestViewModel
import com.lzy.corebiz.httpservice.bean.UserBean
import com.lzy.corebiz.login.UserMgr
import kotlinx.coroutines.Job

class LoginViewModel(private val mLoginRepository: LoginRepository = LoginRepository()) :
    BaseRequestViewModel() {

    private val mUserBean = MutableLiveData<UserBean>()
    val loginUserBean: LiveData<UserBean> = mUserBean

    private var mLoginJob: Job? = null

    fun login(username: String, password: String) {
        mLoginJob = launchLiveDataHandlerRequest {
            val userBean = handleBaseResult(mLoginRepository.login(username, password))
            userBean?.let {
                mUserBean.value = it
                UserMgr.updateUserBean(it)
            }
        }
    }

    fun cancelLogin() {
        mLoginJob?.cancel()
    }

}