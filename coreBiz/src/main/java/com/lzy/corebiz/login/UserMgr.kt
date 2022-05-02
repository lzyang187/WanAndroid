package com.lzy.corebiz.login

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.CacheDiskStaticUtils
import com.lzy.corebiz.httpservice.bean.UserBean

/**
 * Created by zhaoyang.li5 on 2022/4/30 14:16
 */
object UserMgr {

    private const val KEY_USER_BEAN = "key_user_bean"

    private val mUserBeanLiveData = MutableLiveData<UserBean?>()
    val getUserBeanLiveData = mUserBeanLiveData

    fun init() {
        val userBean = CacheDiskStaticUtils.getSerializable(KEY_USER_BEAN) as UserBean?
        userBean?.let {
            mUserBeanLiveData.value = userBean
        }
    }

    fun updateUserBean(userBean: UserBean) {
        mUserBeanLiveData.value = userBean
        CacheDiskStaticUtils.put(KEY_USER_BEAN, userBean)
    }

    fun logOut() {
        mUserBeanLiveData.value = null
        CacheDiskStaticUtils.remove(KEY_USER_BEAN)
    }

}