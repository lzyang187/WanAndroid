package com.lzy.wanandroid.settings

import com.lzy.corebiz.AppConfig
import com.lzy.libview.BaseActivity
import com.lzy.wanandroid.R
import com.lzy.wanandroid.databinding.ActivityAboutUsBinding

class AboutUsActivity : BaseActivity<ActivityAboutUsBinding>() {

    override fun initViewBinding(): ActivityAboutUsBinding {
        return ActivityAboutUsBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        initToolbar(mBinding.toolbarLayout.toolbar)
        mBinding.versionTv.text =
            String.format(getString(R.string.version_name), AppConfig.VERSION_NAME)
    }

}