package com.lzy.wanandroid.ui.home

import com.lzy.corebiz.httpservice.WanAndroidHttpService
import com.lzy.corebiz.httpservice.bean.ArticleBean
import com.lzy.corebiz.httpservice.bean.ArticleData
import com.lzy.corebiz.httpservice.bean.BannerBean
import com.lzy.corebiz.httpservice.bean.BaseResult
import com.lzy.libhttp.RetrofitBuildHelper
import javax.inject.Inject

/**
 * Created by zhaoyang.li5 on 2022/4/27 9:45
 */
class HomeRepository @Inject constructor() {

    suspend fun requestArticleList(pageIndex: Int): BaseResult<ArticleData> {
        val httpService = RetrofitBuildHelper.create(WanAndroidHttpService::class.java)
        return httpService.articleList(pageIndex)
    }

    suspend fun requestTopArticleList(): BaseResult<List<ArticleBean>> {
        val httpService = RetrofitBuildHelper.create(WanAndroidHttpService::class.java)
        return httpService.topArticleList()
    }

    suspend fun requestBanner(): BaseResult<List<BannerBean>> {
        val httpService = RetrofitBuildHelper.create(WanAndroidHttpService::class.java)
        return httpService.banner()
    }

    suspend fun collectArticle(id: Int): BaseResult<Unit> {
        val httpService = RetrofitBuildHelper.create(WanAndroidHttpService::class.java)
        return httpService.collectArticle(id)
    }

    suspend fun unCollectArticle(id: Int): BaseResult<Unit> {
        val httpService = RetrofitBuildHelper.create(WanAndroidHttpService::class.java)
        return httpService.unCollectArticle(id)
    }
}