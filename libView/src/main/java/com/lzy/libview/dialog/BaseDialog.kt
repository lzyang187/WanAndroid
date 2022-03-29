package com.lzy.libview.dialog

import android.app.Dialog
import android.content.Context
import android.view.KeyEvent
import android.view.Window
import com.lzy.libview.R

/**
 * 基础Dialog
 * @author: cyli8
 * @date: 2019/1/24 10:46
 */
open class BaseDialog(context: Context, animStyleID: Int) : Dialog(context, R.style.lib_view_theme_basedialog) {

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        if (animStyleID > 0) {
            window?.setWindowAnimations(animStyleID)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (onKeyBack()) {
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    protected fun onKeyBack(): Boolean {
        return false
    }

}