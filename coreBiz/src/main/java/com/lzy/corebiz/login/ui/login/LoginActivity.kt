package com.lzy.corebiz.login.ui.login

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.lzy.corebiz.R
import com.lzy.corebiz.databinding.CoreBizActivityLoginBinding
import com.lzy.corebiz.httpservice.bean.UserBean
import com.lzy.corebiz.login.register.RegisterActivity
import com.lzy.libhttp.exception.HttpRequestError
import com.lzy.libview.BaseActivity

class LoginActivity : BaseActivity<CoreBizActivityLoginBinding>() {

    private lateinit var mLoginViewModel: LoginViewModel

    override fun initViewBinding(): CoreBizActivityLoginBinding {
        return CoreBizActivityLoginBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        initToolbar(mBinding.toolbarLayout.toolbar)
        mLoginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        mLoginViewModel.getHttpRequestErrorLiveData().observe(this, Observer {
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
        mLoginViewModel.loginUserBean.observe(this, Observer {
            updateUiWithUser(it)
            setResult(Activity.RESULT_OK)
            //Complete and destroy login activity once successful
            finish()
        })

        mBinding.username.addTextChangedListener {
            it?.let {
                if (TextUtils.isEmpty(it)) {
                    mBinding.login.isEnabled = false
                } else {
                    mBinding.login.isEnabled = mBinding.password.text?.isNotEmpty() == true
                }
            }
        }
        mBinding.password.addTextChangedListener {
            it?.let {
                if (TextUtils.isEmpty(it)) {
                    mBinding.login.isEnabled = false
                } else {
                    mBinding.login.isEnabled = mBinding.username.text?.isNotEmpty() == true
                }
            }
        }
        // ??????????????????
        mBinding.login.setOnClickListener {
            showWaitingDialog(this) {
                mLoginViewModel.cancelLogin()
            }
            mLoginViewModel.login(
                mBinding.username.text.toString(), mBinding.password.text.toString()
            )
        }
        KeyboardUtils.showSoftInput(mBinding.username)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.core_biz_login_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.register -> {
                ActivityUtils.startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
        return true
    }

    private fun updateUiWithUser(model: UserBean) {
        dismissWaitingDialog()
        val welcome = getString(R.string.core_biz_welcome)
        val displayName = model.nickname
        toast("$welcome???$displayName???")
    }

    private fun updateUiWithTip(errorTip: String) {
        dismissWaitingDialog()
        val failed = getString(R.string.core_biz_login_failed)
        toast("$failed???$errorTip")
    }

    companion object {
        fun startLoginActivity(activity: FragmentActivity) {
            val intent = Intent(activity, LoginActivity::class.java)
            // TODO: 2022/5/2
            activity.startActivityForResult(intent, 1)
        }
    }
}