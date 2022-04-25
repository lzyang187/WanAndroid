package com.lzy.libview

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding


/**
 * Created by zhaoyang.li5 on 2022/4/3 14:32
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity(), IBaseView {

    protected open lateinit var mBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = initViewBinding()
        setContentView(mBinding.root)
        intent.extras?.let { getIntentExtras(it) }
        initView()
        requestOrLoadData()
    }

    protected abstract fun initViewBinding(): VB

    protected open fun getIntentExtras(extras: Bundle) {

    }

    protected open fun initView() {

    }

    protected open fun initToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.lib_view_arrow_back_24)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    protected open fun requestOrLoadData() {

    }

    override fun isViewAttached(): Boolean {
        return !isFinishing && !isDestroyed
    }

    override fun toast(text: CharSequence?) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun toast(resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
    }
}