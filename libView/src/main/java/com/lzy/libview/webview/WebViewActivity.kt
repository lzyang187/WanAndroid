package com.lzy.libview.webview

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.RelativeLayout
import com.lzy.libview.BaseActivity
import com.lzy.libview.databinding.LibViewActivityWebViewBinding

/**
 * Created by zhaoyang.li5 on 2022/4/25 9:07
 */
class WebViewActivity : BaseActivity<LibViewActivityWebViewBinding>(),
    WebViewHelper.OnWebViewListener, WebViewHelper.OnWebChromeListener {

    companion object {
        private const val EXTRA_WEB_VIEW_URL = "extra_web_view_url"

        fun openWebViewActivity(context: Context, url: String) {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(EXTRA_WEB_VIEW_URL, url)
            context.startActivity(intent)
        }
    }

    override fun initViewBinding(): LibViewActivityWebViewBinding {
        return LibViewActivityWebViewBinding.inflate(layoutInflater)
    }

    private var mUrl: String? = null
    private lateinit var mWebView: SafeWebView

    override fun getIntentExtras(extras: Bundle) {
        super.getIntentExtras(extras)
        mUrl = extras.getString(EXTRA_WEB_VIEW_URL)
    }

    override fun initView() {
        super.initView()
        initToolbar(mBinding.toolbar)
        buildWebView()
        mUrl?.let {
            mWebView.loadUrl(it)
        }
    }

    private fun buildWebView() {
        val webViewHelper = WebViewHelper(this)
        webViewHelper.setWebViewListener(this)
        webViewHelper.setWebChromeListener(this)
        mWebView = webViewHelper.initWebView()
        val layoutParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        mBinding.webviewLayout.addView(mWebView, layoutParams)
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        pageStart()
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        pageEnd()
    }

    override fun onReceivedError(
        view: WebView?, request: WebResourceRequest?, error: WebResourceError?
    ) {
        pageEnd()
    }

    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        pageEnd()
    }

    override fun onKeyBack(): Boolean {
        handleGoBack()
        return true
    }

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        changeProgress(newProgress)
    }

    override fun onReceivedTitle(view: WebView?, title: String?) {
        refreshTitle(title)
    }

    private fun refreshTitle(title: String?) {
        mBinding.toolbar.title = title
    }

    override fun onJsAlert(
        view: WebView?, url: String?, message: String?, result: JsResult?
    ): Boolean {
        return false
    }

    private fun pageStart() {
        mBinding.webViewPb.visibility = View.VISIBLE
    }

    private fun pageEnd() {
        mBinding.webViewPb.visibility = View.GONE
    }

    private fun changeProgress(progress: Int) {
        mBinding.webViewPb.progress = progress
    }

    override fun onBackPressed() {
        handleGoBack()
    }

    private fun handleGoBack() {
        if (mWebView.canGoBack()) {
            mWebView.goBack()
        } else {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.webviewLayout.removeAllViews()
        mWebView.visibility = View.GONE
        mWebView.removeAllViews()
        mWebView.destroy()
    }

}