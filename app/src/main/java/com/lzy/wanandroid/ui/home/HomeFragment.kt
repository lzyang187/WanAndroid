package com.lzy.wanandroid.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lzy.corebiz.httpservice.bean.ArticleBean
import com.lzy.corebiz.httpservice.bean.BannerBean
import com.lzy.corebiz.login.ui.login.LoginActivity
import com.lzy.libhttp.exception.HttpRequestError
import com.lzy.libview.BaseAdapter
import com.lzy.libview.BaseFragment
import com.lzy.libview.banner.IBannerData
import com.lzy.libview.webview.WebViewActivity
import com.lzy.wanandroid.R
import com.lzy.wanandroid.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment @Inject constructor() : BaseFragment<FragmentHomeBinding>(),
    BaseAdapter.OnItemClickListener<ArticleBean> {

    private val mViewModel: HomeViewModel by viewModels()
    private lateinit var mAdapter: HomeAdapter

    override fun initViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        mBinding.pullLayout.setOnRefreshListener { onRefreshData() }
        mBinding.pullLayout.setOnLoadMoreListener { onLoadMore() }
        mBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        mBinding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                context, DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun onRefreshData() {
        mViewModel.refresh()
    }

    private fun onLoadMore() {
        mViewModel.loadMore()
    }

    override fun initViewModel() {
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
            context?.let { con: Context ->
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
                    mAdapter = HomeAdapter(con, it, this, mViewModel)
                    if (mViewModel.getBannerLiveData().value?.isNotEmpty() == true) {
                        mAdapter.setBannerList(
                            mViewModel.getBannerLiveData().value as List<BannerBean>,
                            mBannerItemClickListener
                        )
                    }
                    mBinding.recyclerView.adapter = mAdapter
                }
            }
        })
        mViewModel.getBannerLiveData().observe(this, {
            context?.let { con: Context ->
                if (::mAdapter.isInitialized) {
                    mAdapter.setBannerList(it, mBannerItemClickListener)
                }
            }
        })
        mViewModel.getNotifyPosition.observe(this, Observer {
            if (::mAdapter.isInitialized) {
                mAdapter.notifyItemChanged(it)
            }
        })
        mViewModel.getToastLiveData.observe(this, Observer {
            toast(it)
        })
        mViewModel.getNeedLoginLiveData.observe(this, Observer {
            activity?.let {
                LoginActivity.startLoginActivity(it)
            }
        })
    }

    private val mBannerItemClickListener = object : BaseAdapter.OnItemClickListener<IBannerData> {
        override fun onItemClick(position: Int, data: IBannerData?) {
            (data as BannerBean?)?.url?.let {
                activity?.let { activity -> WebViewActivity.openWebViewActivity(activity, it) }
            }
        }
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

    override fun requestOrLoadData() {
        hideEmptyView()
        mBinding.pullLayout.autoRefreshAnimationOnly()
        mViewModel.refresh()
    }

    override fun onItemClick(position: Int, data: ArticleBean?) {
        data?.link?.let { link: String ->
            activity?.let { activity -> WebViewActivity.openWebViewActivity(activity, link) }
        }
    }


}