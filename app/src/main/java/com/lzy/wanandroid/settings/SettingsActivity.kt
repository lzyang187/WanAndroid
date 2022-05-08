package com.lzy.wanandroid.settings

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.lzy.libview.BaseActivity
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
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
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

                    }
                }
            }
            return true
        }

    }

    override fun initView() {
        super.initView()
        initToolbar(mBinding.toolbarLayout.toolbar)
    }
}