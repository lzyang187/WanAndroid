package com.lzy.corebiz.httpservice.bean

import java.io.Serializable

/**
 *
 * Created by zhaoyang.li5 on 2022/4/14 8:32
 */
data class ArticleBean(
    val apkLink: String?,
    val audit: Int?,
    val author: String?,
    val canEdit: Boolean?,
    val chapterId: Int?,
    val chapterName: String?,
    var collect: Boolean?,
    val courseId: Int?,
    val desc: String?,
    val descMd: String?,
    val envelopePic: String?,
    val fresh: Boolean?,
    val host: String?,
    val id: Int?,
    val link: String?,
    val niceDate: String?,
    val niceShareDate: String?,
    val origin: String?,
    val prefix: String?,
    val projectLink: String?,
    val publishTime: Long?,
    val realSuperChapterId: Int?,
    val selfVisible: Int?,
    val shareDate: Long?,
    val shareUser: String?,
    val superChapterId: Int?,
    val superChapterName: String?,
    val tags: List<TagBean>?,
    val title: String?,
    val type: Int?,
    val userId: Int?,
    val visible: Int?,
    val zan: Int?
) : Serializable {
    var top = false

    fun getAuthorName(): String {
        return if (author.isNullOrEmpty()) {
            if (shareUser.isNullOrEmpty()) {
                "佚名"
            } else {
                shareUser
            }
        } else {
            author
        }
    }

    fun getTagName(): String? {
        return tags?.firstOrNull()?.name
    }

    fun getChapter(): String? {
        return if (superChapterName.isNullOrEmpty()) {
            if (chapterName.isNullOrEmpty()) {
                null
            } else {
                chapterName
            }
        } else {
            if (chapterName.isNullOrEmpty()) {
                superChapterName
            } else {
                "$superChapterName/$chapterName"
            }
        }
    }

}