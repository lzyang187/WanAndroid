package com.lzy.corebiz.httpservice

import com.lzy.corebiz.httpservice.bean.ArticleBean
import com.lzy.corebiz.httpservice.bean.ArticleData
import com.lzy.corebiz.httpservice.bean.BaseResult
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by zhaoyang.li5 on 2022/4/12 8:39
 */
interface WanAndroidHttpService {

    /**
     * https://www.wanandroid.com/article/list/0/json
     * 方法：GET
     * 参数：页码，拼接在连接中，从0开始。
     */
    @GET("article/list/{page}/json")
    suspend fun articleList(@Path("page") page: Int): BaseResult<ArticleData>

    @GET("article/top/json")
    suspend fun topArticleList(): BaseResult<List<ArticleBean>>

}
