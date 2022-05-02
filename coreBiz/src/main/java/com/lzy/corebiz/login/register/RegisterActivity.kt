package com.lzy.corebiz.login.register

import android.app.Activity
import android.text.TextUtils
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.KeyboardUtils
import com.lzy.corebiz.R
import com.lzy.corebiz.databinding.CoreBizActivityRegisterBinding
import com.lzy.corebiz.httpservice.bean.UserBean
import com.lzy.libhttp.exception.HttpRequestError
import com.lzy.libview.BaseActivity

class RegisterActivity : BaseActivity<CoreBizActivityRegisterBinding>() {

    override fun initViewBinding(): CoreBizActivityRegisterBinding {
        return CoreBizActivityRegisterBinding.inflate(layoutInflater)
    }

    private lateinit var mRegisterViewModel: RegisterViewModel

    override fun initView() {
        super.initView()
        initToolbar(mBinding.toolbarLayout.toolbar)
        mRegisterViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        mRegisterViewModel.getHttpRequestErrorLiveData().observe(this, Observer {
            when (it) {
                is HttpRequestError.NetworkError -> {
                    updateUiWithTip(getString(R.string.lib_http_network_error))
                }
                is HttpRequestError.TimeoutError -> {
                    updateUiWithTip(getString(R.string.lib_http_network_timeout))
                }
                is HttpRequestError.ServerError -> {
                    it.errorMsg?.let { errorMsg ->
                        updateUiWithTip(errorMsg)
                    } ?: run {
                        updateUiWithTip(getString(R.string.lib_http_server_error))
                    }
                }
                is HttpRequestError.EmptyError -> {
                    updateUiWithTip(getString(R.string.lib_http_result_empty))
                }
            }
        })
        mRegisterViewModel.registerUserBeanLiveData.observe(this, Observer {
            updateUiWithUser(it)
            setResult(Activity.RESULT_OK)
            //Complete and destroy login activity once successful
            finish()
        })
        mBinding.username.addTextChangedListener {
            it?.let {
                if (TextUtils.isEmpty(it)) {
                    mBinding.register.isEnabled = false
                } else {
                    mBinding.register.isEnabled =
                        mBinding.password.text?.isNotEmpty() == true && mBinding.rePassword.text?.isNotEmpty() == true
                }
            }
        }
        mBinding.password.addTextChangedListener {
            it?.let {
                if (TextUtils.isEmpty(it)) {
                    mBinding.register.isEnabled = false
                } else {
                    mBinding.register.isEnabled =
                        mBinding.username.text?.isNotEmpty() == true && mBinding.rePassword.text?.isNotEmpty() == true
                }
            }
        }
        mBinding.rePassword.addTextChangedListener {
            it?.let {
                if (TextUtils.isEmpty(it)) {
                    mBinding.register.isEnabled = false
                } else {
                    mBinding.register.isEnabled =
                        mBinding.username.text?.isNotEmpty() == true && mBinding.password.text?.isNotEmpty() == true
                }
            }
        }
        // 点击事件
        mBinding.register.setOnClickListener {
            showWaitingDialog(this) {
                mRegisterViewModel.cancelRegister()
            }
            mRegisterViewModel.register(
                mBinding.username.text.toString(),
                mBinding.password.text.toString(),
                mBinding.rePassword.text.toString()
            )
        }
        KeyboardUtils.showSoftInput(mBinding.username)
    }

    private fun updateUiWithUser(model: UserBean) {
        dismissWaitingDialog()
        val welcome = getString(R.string.core_biz_welcome)
        val displayName = model.nickname
        toast("$welcome，$displayName！")
    }

    private fun updateUiWithTip(errorTip: String) {
        dismissWaitingDialog()
        val failed = getString(R.string.core_biz_register_failed)
        toast("$failed，$errorTip")
    }

}