package com.lzy.wanandroid.collect

import com.lzy.corebiz.httpservice.WanAndroidHttpService
import com.lzy.corebiz.httpservice.bean.ArticleData
import com.lzy.corebiz.httpservice.bean.BaseResult
import com.lzy.libhttp.RetrofitBuildHelper
import javax.inject.Inject

/**
 * Created by zhaoyang.li5 on 2022/5/15 18:35
 */
class CollectRepository @Inject constructor() {

    suspend fun collectArticle(id: Int): BaseResult<Unit> {
        val httpService = RetrofitBuildHelper.create(WanAndroidHttpService::class.java)
        return httpService.collectArticle(id)
    }

    suspend fun unCollectArticle(id: Int): BaseResult<Unit> {
        val httpService = RetrofitBuildHelper.create(WanAndroidHttpService::class.java)
        return httpService.unCollectArticle(id)
    }

    suspend fun unCollectArticleInCollectPage(id: Int, originId: Int): BaseResult<Unit> {
        val httpService = RetrofitBuildHelper.create(WanAndroidHttpService::class.java)
        return httpService.unCollectArticleInCollectPage(id, originId)
    }

    suspend fun collectList(pageIndex: Int): BaseResult<ArticleData> {
        return RetrofitBuildHelper.create(WanAndroidHttpService::class.java).collectList(pageIndex)
    }

}