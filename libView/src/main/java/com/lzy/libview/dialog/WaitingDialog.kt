package com.lzy.libview.dialog

import android.content.Context
import com.lzy.libview.R
import com.lzy.libview.databinding.LibViewWaitingDialogBinding

/**
 * Created by zhaoyang.li5 on 2022/4/30 19:55
 */
class WaitingDialog(context: Context) :
    BaseDialog<LibViewWaitingDialogBinding>(context, R.style.lib_view_loading_dialog) {

    override fun initViewBinding(): LibViewWaitingDialogBinding {
        return LibViewWaitingDialogBinding.inflate(layoutInflater)
    }


}