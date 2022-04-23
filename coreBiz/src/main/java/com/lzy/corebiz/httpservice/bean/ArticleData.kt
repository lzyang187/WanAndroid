package com.lzy.corebiz.httpservice.bean

import java.io.Serializable

/**
 * Created by zhaoyang.li5 on 2022/4/17 11:14
 */
data class ArticleData(
    val curPage: Int?,
    val datas: List<ArticleBean>?,
    val offset: Int?,
    val over: Boolean?,
    val pageCount: Int?,
    val size: Int?,
    val total: Int?
) : Serializable