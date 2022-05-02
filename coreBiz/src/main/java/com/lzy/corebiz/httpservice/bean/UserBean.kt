package com.lzy.corebiz.httpservice.bean

import java.io.Serializable

/**
 * Created by zhaoyang.li5 on 2022/4/28 15:01
 */
data class UserBean(
    val admin: Boolean?, // false
    val chapterTops: List<Any>?,
    val coinCount: Int?, // 10
    val collectIds: List<Any>?,
    val email: String?,
    val icon: String?,
    val id: Int?, // 5303
    val nickname: String?, // lzyang187
    val password: String?,
    val publicName: String?, // lzyang187
    val token: String?,
    val type: Int?, // 0
    val username: String? // lzyang187
) : Serializable