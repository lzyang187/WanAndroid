package com.lzy.corebiz.login.register

import androidx.lifecycle.MutableLiveData
import com.lzy.corebiz.BaseRequestViewModel
import com.lzy.corebiz.httpservice.bean.UserBean
import kotlinx.coroutines.Job

/**
 * Created by zhaoyang.li5 on 2022/5/2 14:19
 */
class RegisterViewModel(private val mRepository: RegisterRepository = RegisterRepository()) :
    BaseRequestViewModel() {

    private var mRegisterJob: Job? = null

    private val mUserBean = MutableLiveData<UserBean>()
    val registerUserBeanLiveData = mUserBean

    fun register(username: String, password: String, repassword: String) {
        mRegisterJob = launchLiveDataHandlerRequest {
            val userBean = handleBaseResult(mRepository.register(username, password, repassword))
            userBean?.let {
                registerUserBeanLiveData.value = it
            }
        }
    }

    fun cancelRegister() {
        mRegisterJob?.cancel()
    }

}