package com.lzy.wanandroid.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lzy.corebiz.httpservice.bean.ArticleBean
import com.lzy.corebiz.httpservice.bean.BannerBean
import com.lzy.libhttp.exception.HttpRequestError
import com.lzy.wanandroid.collect.BaseCollectViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseCollectViewModel() {

    @Inject
    lateinit var mHomeRepository: HomeRepository

    private var mArticleListLiveData = MutableLiveData<List<ArticleBean>>()
    private val mArticleList = mutableListOf<ArticleBean>()

    fun getArticleListLiveData() = mArticleListLiveData

    private var mPageIndex: Int = 0

    fun isRefresh() = mPageIndex == 0

    private var mNoMore = false

    fun isNoMore() = mNoMore

    fun refresh() {
        mNoMore = false
        mArticleList.clear()
        mBannerList.clear()
        requestTopArticleList()
        mPageIndex = 0
        requestArticleList()
        requestBanner()
    }

    fun loadMore() {
        requestArticleList()
    }

    private fun requestArticleList() {
        launchLiveDataHandlerRequest {
            val result = mHomeRepository.requestArticleList(mPageIndex)
            if (result.success()) {
                mNoMore = result.data?.over == true
                result.data?.datas?.let {
                    if (it.isEmpty()) {
                        // 返回结果为空
                        mHttpRequestErrorLiveData.value = HttpRequestError.EmptyError
                    } else {
                        mArticleList.addAll(it)
                        mArticleListLiveData.value = mArticleList
                        // 每次请求成功后页码加+1
                        mPageIndex++
                    }
                } ?: run {
                    // 返回结果为空
                    mHttpRequestErrorLiveData.value = HttpRequestError.EmptyError
                }
            } else {
                mHttpRequestErrorLiveData.value = HttpRequestError.NetworkError
            }
        }
    }

    private fun requestTopArticleList() {
        viewModelScope.launch(CoroutineExceptionHandler { _, _ ->
            // 置顶文章请求失败则不处理
        }) {
            val result = mHomeRepository.requestTopArticleList()
            if (result.success()) {
                result.data?.let {
                    it.forEach { bean: ArticleBean ->
                        bean.top = true
                    }
                    mArticleList.addAll(0, it)
                    mArticleListLiveData.value = mArticleList
                }
            }
        }
    }

    private val mBannerLiveData = MutableLiveData<List<BannerBean>>()
    private val mBannerList = mutableListOf<BannerBean>()

    fun getBannerLiveData() = mBannerLiveData

    private fun requestBanner() {
        viewModelScope.launch(CoroutineExceptionHandler { _, _ ->
            // banner请求失败则不处理
        }) {
            val result = mHomeRepository.requestBanner()
            if (result.success()) {
                result.data?.let {
                    mBannerList.addAll(it)
                    mBannerLiveData.value = mBannerList
                }
            }
        }
    }
}