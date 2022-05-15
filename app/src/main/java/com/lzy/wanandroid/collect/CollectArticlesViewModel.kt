package com.lzy.wanandroid.collect

import androidx.lifecycle.MutableLiveData
import com.lzy.corebiz.httpservice.bean.ArticleBean
import com.lzy.libhttp.exception.HttpRequestError
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by zhaoyang.li5 on 2022/5/11 14:29
 */
@HiltViewModel
class CollectArticlesViewModel @Inject constructor() : BaseCollectViewModel() {

    @Inject
    lateinit var mRepository: CollectRepository

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
        mPageIndex = 0
        collectList()
    }

    fun loadMore() {
        collectList()
    }

    private fun collectList() {
        launchLiveDataHandlerRequest {
            val result = mRepository.collectList(mPageIndex)
            val articleData = handleBaseResult(result)
            articleData?.let {
                mNoMore = it.over == true
                it.datas?.let { datas ->
                    if (datas.isNotEmpty()) {
                        datas.forEach { articleBean ->
                            articleBean.collect = true
                            mArticleList.add(articleBean)
                        }
                        mArticleListLiveData.value = mArticleList
                        // 每次请求成功后页码加+1
                        mPageIndex++
                    } else {
                        // 返回结果为空
                        mHttpRequestErrorLiveData.value = HttpRequestError.EmptyError
                    }
                } ?: run {
                    // 返回结果为空
                    mHttpRequestErrorLiveData.value = HttpRequestError.EmptyError
                }
            }
        }
    }
}