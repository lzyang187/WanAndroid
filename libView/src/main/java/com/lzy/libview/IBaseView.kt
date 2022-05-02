package com.lzy.libview

import android.content.Context
import android.content.DialogInterface
import androidx.annotation.StringRes

/**
 * Created by zhaoyang.li5 on 2022/4/3 14:25
 */
interface IBaseView {

    /**
     * 页面是否已加载
     * @return true,已加载
     */
    fun isViewAttached(): Boolean

    /**
     * toast
     * @param text 文本
     */
    fun toast(text: CharSequence?)

    /**
     * toast
     * @param resId 资源id
     */
    fun toast(@StringRes resId: Int)

    fun showWaitingDialog(context: Context) {

    }

    fun showWaitingDialog(context: Context, cancelListener: DialogInterface.OnCancelListener) {

    }

    fun dismissWaitingDialog() {

    }

}