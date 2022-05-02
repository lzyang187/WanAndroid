package com.lzy.corebiz.httpservice

import com.lzy.corebiz.httpservice.bean.*
import retrofit2.http.*

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

    /**
     * https://www.wanandroid.com/tree/json
     * 方法：GET
     * 参数：无
     */
    @GET("article/top/json")
    suspend fun topArticleList(): BaseResult<List<ArticleBean>>

    /**
     * https://www.wanandroid.com/banner/json
     * 方法：GET
     * 参数：无
     */
    @GET("banner/json")
    suspend fun banner(): BaseResult<List<BannerBean>>

    /**
     * https://www.wanandroid.com/user/login
     * 方法：POST
     * 参数：username，password
     * 登录后会在cookie中返回账号密码，只要在客户端做cookie持久化存储即可自动登录验证。
     */
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String, @Field("password") password: String
    ): BaseResult<UserBean>

}
