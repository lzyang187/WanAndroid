package com.lzy.wanandroid.collect

import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lzy.corebiz.httpservice.bean.ArticleBean
import com.lzy.corebiz.login.ui.login.LoginActivity
import com.lzy.libhttp.exception.HttpRequestError
import com.lzy.libview.BaseActivity
import com.lzy.libview.BaseAdapter
import com.lzy.libview.webview.WebViewActivity
import com.lzy.wanandroid.R
import com.lzy.wanandroid.databinding.ActivityCollectArticlesBinding
import com.lzy.wanandroid.ui.home.HomeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectArticlesActivity : BaseActivity<ActivityCollectArticlesBinding>(),
    BaseAdapter.OnItemClickListener<ArticleBean> {

    private val mViewModel: CollectArticlesViewModel by viewModels()
    private lateinit var mAdapter: HomeAdapter

    override fun initViewBinding(): ActivityCollectArticlesBinding {
        return ActivityCollectArticlesBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        initToolbar(mBinding.toolbarLayout.toolbar)
        mBinding.pullLayout.setOnRefreshListener { onRefreshData() }
        mBinding.pullLayout.setOnLoadMoreListener { onLoadMore() }
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mBinding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL
            )
        )
        mViewModel.getHttpRequestErrorLiveData().observe(this, { error ->
            when (error) {
                is HttpRequestError.NetworkError -> {
                    if (mViewModel.isRefresh()) {
                        showEmptyView(getString(R.string.lib_http_network_error))
                    } else {
                        loadMoreError(R.string.lib_http_network_error)
                    }
                }
                is HttpRequestError.TimeoutError -> {
                    if (mViewModel.isRefresh()) {
                        showEmptyView(getString(R.string.lib_http_network_timeout))
                    } else {
                        loadMoreError(R.string.lib_http_network_timeout)
                    }
                }
                is HttpRequestError.ServerError -> {
                    if (mViewModel.isRefresh()) {
                        showEmptyView(getString(R.string.lib_http_server_error))
                    } else {
                        loadMoreError(R.string.lib_http_server_error)
                    }
                }
                is HttpRequestError.EmptyError -> {
                    if (mViewModel.isRefresh()) {
                        showEmptyView(getString(R.string.lib_http_result_empty))
                    } else {
                        loadMoreError(R.string.lib_http_result_empty)
                    }
                }
            }
        })
        mViewModel.getArticleListLiveData().observe(this, {
            when {
                mViewModel.isRefresh() -> {
                    hideEmptyView()
                    mBinding.pullLayout.finishRefresh()

                }
                else -> {
                    mBinding.pullLayout.finishLoadMore()
                }
            }
            if (mViewModel.isNoMore()) {
                mBinding.pullLayout.finishLoadMoreWithNoMoreData()
            }
            if (::mAdapter.isInitialized) {
                mAdapter.notifyDataSetChanged()
            } else {
                mAdapter = HomeAdapter(this, it, this, mViewModel)
                mAdapter.setSupportHeader(false)
                mBinding.recyclerView.adapter = mAdapter
            }
        })
        mViewModel.getNotifyPosition.observe(this, {
            if (::mAdapter.isInitialized) {
                mAdapter.notifyItemChanged(it)
            }
        })
        mViewModel.getToastLiveData.observe(this, {
            toast(it)
        })
        mViewModel.getNeedLoginLiveData.observe(this, {
            LoginActivity.startLoginActivity(this)
        })
    }

    override fun requestOrLoadData() {
        hideEmptyView()
        mBinding.pullLayout.autoRefreshAnimationOnly()
        mViewModel.refresh()
    }

    private fun onRefreshData() {
        mViewModel.refresh()
    }

    private fun onLoadMore() {
        mViewModel.loadMore()
    }

    private fun showEmptyView(tip: String) {
        mBinding.pullLayout.finishRefresh()
        mBinding.pullLayout.setEnableRefresh(false)
        mBinding.pullLayout.setEnableLoadMore(false)
        mBinding.recyclerView.visibility = View.GONE
        mBinding.emptyView.show(tip) {
            requestOrLoadData()
        }
    }

    private fun hideEmptyView() {
        mBinding.pullLayout.setEnableRefresh(true)
        mBinding.pullLayout.setEnableLoadMore(true)
        mBinding.emptyView.hide()
        mBinding.recyclerView.visibility = View.VISIBLE
    }

    private fun loadMoreError(@StringRes resId: Int) {
        toast(resId)
        mBinding.pullLayout.finishLoadMore(false)
    }

    override fun onItemClick(position: Int, data: ArticleBean?) {
        data?.link?.let { link: String ->
            WebViewActivity.openWebViewActivity(this, link)
        }
    }
}