package com.lzy.libview.webview

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.blankj.utilcode.util.LogUtils
import com.lzy.libutils.DarkModeUtil

/**
 * Created by zhaoyang.li5 on 2022/4/25 9:36
 */
class WebViewHelper(private val mContext: Context) {
    interface OnWebViewListener {
        fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?)
        fun onPageFinished(view: WebView?, url: String?)
        fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?)
        fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?)
        fun onKeyBack(): Boolean
    }

    private var mWebViewListener: OnWebViewListener? = null

    fun setWebViewListener(listener: OnWebViewListener?) {
        mWebViewListener = listener
    }

    interface OnWebChromeListener {
        fun onProgressChanged(view: WebView?, newProgress: Int)
        fun onReceivedTitle(view: WebView?, title: String?)
        fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean
    }

    private var mWebChromeListener: OnWebChromeListener? = null

    fun setWebChromeListener(listener: OnWebChromeListener?) {
        mWebChromeListener = listener
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun initWebView(): SafeWebView {
        val webView = SafeWebView(mContext)
        // setWebViewClient部分
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return shouldOverrideUrlLoading(view, Uri.parse(url))
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?, request: WebResourceRequest?
            ): Boolean {
                return shouldOverrideUrlLoading(view, request?.url)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                LogUtils.iTag(TAG, "onPageStarted")
                mWebViewListener?.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                LogUtils.iTag(TAG, "onPageFinished")
                mWebViewListener?.onPageFinished(view, url)
            }

            override fun onReceivedError(
                view: WebView?, request: WebResourceRequest?, error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                LogUtils.eTag(TAG, "onReceivedError: url加载失败:" + request?.url)
                LogUtils.eTag(
                    TAG, "onReceivedError:" + error?.description + error?.errorCode
                )
                mWebViewListener?.onReceivedError(view, request, error)
            }

            override fun onReceivedSslError(
                view: WebView?, handler: SslErrorHandler?, error: SslError?
            ) {
                LogUtils.eTag(TAG, "onReceivedSslError: 页面加载失败")
                mWebViewListener?.onReceivedSslError(view, handler, error)
            }
        }
        // 按键监听部分
        webView.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (mWebViewListener?.onKeyBack() == true) {
                        return@OnKeyListener true
                    }
                }
            }
            false
        })
        // setWebChromeClient部分
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                LogUtils.iTag(TAG, "onProgressChanged")
                mWebChromeListener?.onProgressChanged(view, newProgress)
            }

            override fun onReceivedTitle(view: WebView, title: String) {
                super.onReceivedTitle(view, title)
                LogUtils.iTag(TAG, "onReceivedTitle")
                mWebChromeListener?.onReceivedTitle(view, title)
            }

            override fun onJsAlert(
                view: WebView, url: String, message: String, result: JsResult
            ): Boolean {
                LogUtils.iTag(TAG, "onJsAlert : $message")
                if (mWebChromeListener?.onJsAlert(view, url, message, result) == true) {
                    result.confirm()
                    return true
                }
                return super.onJsAlert(view, url, message, result)
            }
        }
        // WebSettings部分
        webView.requestFocus(View.FOCUS_DOWN)
        val settings: WebSettings = webView.settings
        settings.databaseEnabled = true
        // 不缓存
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        settings.setGeolocationEnabled(true)
        settings.allowContentAccess = true
        settings.blockNetworkLoads = false
        settings.blockNetworkImage = false
        settings.domStorageEnabled = true
        settings.allowFileAccess = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.mediaPlaybackRequiresUserGesture = false
        // 这里需要设置为true，才能让WebView支持<meta>标签的viewport属性
        settings.useWideViewPort = true
        settings.setAppCacheEnabled(true)
        val dir = mContext.applicationContext.getDir("appcache", Context.MODE_PRIVATE).path
        settings.setAppCachePath(dir)
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        settings.javaScriptEnabled = true
        WebView.setWebContentsDebuggingEnabled(false)
        // 开启硬件加速，支持播放视频
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.setAcceptThirdPartyCookies(webView, true)
        // 适配深色模式
        val isAppDarkMode: Boolean = DarkModeUtil.isDarkMode(mContext)
        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            if (isAppDarkMode) {
                WebSettingsCompat.setForceDark(settings, WebSettingsCompat.FORCE_DARK_ON)
            } else {
                WebSettingsCompat.setForceDark(settings, WebSettingsCompat.FORCE_DARK_OFF)
            }
        }
        return webView
    }

    private fun shouldOverrideUrlLoading(view: WebView?, uri: Uri?): Boolean {
        if (view == null || uri == null) {
            return false
        }
        LogUtils.iTag(TAG, "shouldOverrideUrlLoading=$uri")
        val scheme = uri.scheme
        if (!(TextUtils.equals(scheme, "http") || TextUtils.equals(scheme, "https"))) {
            try {
                val context = view.context
                val intent = Intent(Intent.ACTION_VIEW, uri)
                context.startActivity(intent)
                return true
            } catch (e: Exception) {

            }
        }
        return false
    }

    companion object {
        private const val TAG = "WebViewHelper"
    }
}