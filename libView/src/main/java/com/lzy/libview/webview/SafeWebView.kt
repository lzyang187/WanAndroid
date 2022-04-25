package com.lzy.libview.webview

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView

/**
 * Created by zhaoyang.li5 on 2022/4/25 9:28
 */
class SafeWebView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    WebView(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    override fun setOverScrollMode(mode: Int) {
        try {
            super.setOverScrollMode(mode)
        } catch (e: Exception) {
        }
    }
}