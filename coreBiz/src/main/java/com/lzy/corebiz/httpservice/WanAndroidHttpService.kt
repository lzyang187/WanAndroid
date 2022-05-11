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

    /**
     * https://www.wanandroid.com/user/logout/json
     * 方法：GET
     * 访问了 logout 后，服务端会让客户端清除 Cookie（即cookie max-Age=0），如果客户端 Cookie 实现合理，
     * 可以实现自动清理，如果本地做了用户账号密码和保存，及时清理
     */
    @GET("user/logout/json")
    suspend fun logout(): BaseResult<Unit>

    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): BaseResult<UserBean>

    /**
     * https://www.wanandroid.com/lg/collect/1165/json
     * 方法：POST
     * 参数： 文章id，拼接在链接中。
     */
    @POST("lg/collect/{id}/json")
    suspend fun collectArticle(@Path("id") id: Int): BaseResult<Unit>

    /**
     * https://www.wanandroid.com/lg/collect/list/0/json
     * 方法：GET
     * 参数： 页码：拼接在链接中，从0开始。
     */
    @GET("lg/collect/list/{page}/json")
    suspend fun collectList(@Path("page") page: Int): BaseResult<ArticleData>

    /**
     * https://www.wanandroid.com/lg/uncollect_originId/2333/json
     * 方法：POST
     * 参数：id:拼接在链接上
     */
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun unCollectArticle(@Path("id") id: Int): BaseResult<Unit>
}
