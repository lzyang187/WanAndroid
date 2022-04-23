package com.lzy.wanandroid.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lzy.libhttp.exception.HttpRequestError
import com.lzy.libview.BaseFragment
import com.lzy.wanandroid.R
import com.lzy.wanandroid.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private lateinit var mViewModel: HomeViewModel
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
        mViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        mViewModel.getHttpRequestErrorLiveData().observe(this, { error ->
            if (mViewModel.isRefresh()) {
                mBinding.pullLayout.finishRefresh()
            } else {
                mBinding.pullLayout.finishLoadMore()
            }
            when (error) {
                is HttpRequestError.NetworkError -> {
                    if (mViewModel.isRefresh()) {
                        showEmptyView(getString(R.string.lib_http_network_error))
                    } else {
                        toast(R.string.lib_http_network_error)
                    }
                }
                is HttpRequestError.TimeoutError -> {
                    if (mViewModel.isRefresh()) {
                        showEmptyView(getString(R.string.lib_http_network_timeout))
                    } else {
                        toast(R.string.lib_http_network_timeout)
                    }
                }
                is HttpRequestError.ServerError -> {
                    if (mViewModel.isRefresh()) {
                        showEmptyView(getString(R.string.lib_http_server_error))
                    } else {
                        toast(R.string.lib_http_server_error)
                    }
                }
                is HttpRequestError.EmptyError -> {
                    if (mViewModel.isRefresh()) {
                        showEmptyView(getString(R.string.lib_http_result_empty))
                    } else {
                        toast(R.string.lib_http_result_empty)
                    }
                }
            }
        })
        mViewModel.getArticleListLiveData().observe(this, {
            context?.let { con: Context ->
                if (mViewModel.isRefresh()) {
                    hideEmptyView()
                    mBinding.pullLayout.finishRefresh()
                } else {
                    mBinding.pullLayout.finishLoadMore()
                }
                if (::mAdapter.isInitialized) {
                    mAdapter.notifyDataSetChanged()
                } else {
                    mAdapter = HomeAdapter(con, it)
                    mBinding.recyclerView.adapter = mAdapter
                }
            }
        })
    }

    private fun showEmptyView(tip: String) {
        mBinding.pullLayout.setEnableRefresh(false)
        mBinding.pullLayout.setEnableLoadMore(false)
        mBinding.recyclerView.visibility = View.GONE
//        mBinding.emptyView.show(
//            false, tip, null, getString(R.string.lib_http_click_retry)
//        ) {
//            requestOrLoadData()
//        }
    }

    private fun hideEmptyView() {
        mBinding.pullLayout.setEnableRefresh(true)
        mBinding.pullLayout.setEnableLoadMore(true)
//        mBinding.emptyView.hide()
        mBinding.recyclerView.visibility = View.VISIBLE
    }

    override fun requestOrLoadData() {
        hideEmptyView()
        mBinding.pullLayout.autoRefresh()
    }

}