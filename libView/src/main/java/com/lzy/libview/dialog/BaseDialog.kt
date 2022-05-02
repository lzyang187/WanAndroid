package com.lzy.libview.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StyleRes
import androidx.viewbinding.ViewBinding
import com.lzy.libview.IBaseView
import com.lzy.libview.R

/**
 * Created by zhaoyang.li5 on 2022/2/23 12:51
 */
abstract class BaseDialog<VB : ViewBinding>(context: Context, @StyleRes themeResId: Int) :
    Dialog(context, themeResId), IBaseView {

    constructor(context: Context) : this(context, R.style.lib_view_theme_basedialog)

    protected open lateinit var mBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = initViewBinding()
        setContentView(mBinding.root)
        initView()
    }

    protected abstract fun initViewBinding(): VB

    protected fun initView() {

    }

    override fun isViewAttached(): Boolean {
        return true
    }

    override fun toast(text: CharSequence?) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun toast(resId: Int) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show()
    }
}