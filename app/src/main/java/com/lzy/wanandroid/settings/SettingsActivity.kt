package com.lzy.wanandroid.settings

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.get
import com.blankj.utilcode.util.ToastUtils
import com.lzy.corebiz.login.UserMgr
import com.lzy.libhttp.exception.HttpRequestError
import com.lzy.libview.BaseActivity
import com.lzy.libview.dialog.WaitingDialog
import com.lzy.libview.webview.WebViewActivity
import com.lzy.wanandroid.R
import com.lzy.wanandroid.databinding.SettingsActivityBinding

class SettingsActivity : BaseActivity<SettingsActivityBinding>() {

    override fun initViewBinding(): SettingsActivityBinding {
        return SettingsActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.settings, SettingsFragment())
                .commit()
        }
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        private val mViewModel: SettingsViewModel by viewModels()
        private var mLogoutPreference: Preference? = null

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            mLogoutPreference = preferenceScreen.get(getString(R.string.core_biz_logout))
            mLogoutPreference?.let {
                if (!UserMgr.isLogin()) {
                    preferenceScreen.removePreference(it)
                }
            }
        }

        override fun onPreferenceTreeClick(preference: Preference): Boolean {
            context?.let {
                when (preference.key) {
                    getString(R.string.official_website) -> {
                        WebViewActivity.openWebViewActivity(
                            it, getString(R.string.official_website_url)
                        )
                    }
                    getString(R.string.source_code) -> {
                        WebViewActivity.openWebViewActivity(
                            it, getString(R.string.source_code_url)
                        )
                    }
                    getString(R.string.copyright) -> {
                        AlertDialog.Builder(it).setTitle(R.string.copyright)
                            .setMessage(R.string.copyright_detail).setCancelable(true)
                            .setPositiveButton(
                                R.string.sure
                            ) { dialog, _ -> dialog?.dismiss() }.create().show()
                    }

                    getString(R.string.about_us) -> {
                        startActivity(Intent(it, AboutUsActivity::class.java))
                    }
                    getString(R.string.core_biz_logout) -> {
                        AlertDialog.Builder(it).setTitle(R.string.core_biz_logout_confirm)
                            .setCancelable(true).setPositiveButton(
                                R.string.sure
                            ) { dialog, _ ->
                                run {
                                    dialog?.dismiss()
                                    logout()
                                }
                            }.setNegativeButton(R.string.cancel) { dialog, _ -> dialog?.dismiss() }
                            .create().show()
                    }
                }
            }
            return true
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            mViewModel.getHttpRequestErrorLiveData().observe(this, Observer {
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
            mViewModel.getLogoutLiveData.observe(this, Observer {
                updateUiWithTip(getString(R.string.core_biz_logout_success))
                mLogoutPreference?.let {
                    preferenceScreen.removePreference(it)
                }
            })
        }

        private fun updateUiWithTip(string: String) {
            dismissWaitingDialog()
            ToastUtils.showShort(string)
        }

        private fun logout() {
            context?.let {
                showWaitingDialog(
                    it
                ) { mViewModel.cancelLogin() }
                mViewModel.logout()
            }
        }

        private var mWaitingDialog: WaitingDialog? = null

        private fun showWaitingDialog(
            context: Context, cancelListener: DialogInterface.OnCancelListener
        ) {
            if (mWaitingDialog == null) {
                mWaitingDialog = WaitingDialog(context)
            }
            mWaitingDialog?.let {
                it.setCancelable(true)
                it.setCanceledOnTouchOutside(true)
                it.setOnCancelListener(cancelListener)
                it.show()
            }
        }

        private fun dismissWaitingDialog() {
            mWaitingDialog?.dismiss()
        }
    }

    override fun initView() {
        super.initView()
        initToolbar(mBinding.toolbarLayout.toolbar)
    }
}