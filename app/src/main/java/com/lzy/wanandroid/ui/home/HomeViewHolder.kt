package com.lzy.wanandroid.ui.home

import android.text.Html
import android.view.View
import com.lzy.corebiz.httpservice.bean.ArticleBean
import com.lzy.libview.BaseViewHolder
import com.lzy.wanandroid.databinding.ItemHomeArticleLayoutBinding

/**
 * Created by zhaoyang.li5 on 2022/4/13 9:22
 */
class HomeViewHolder(binding: ItemHomeArticleLayoutBinding) :
    BaseViewHolder<ArticleBean, ItemHomeArticleLayoutBinding>(binding) {
    override fun bind(position: Int, data: ArticleBean?) {
        data?.apply {
            mBinding.tvTop.visibility = if (top) View.VISIBLE else View.GONE
            mBinding.tvNew.visibility = if (fresh == true) View.VISIBLE else View.GONE
            mBinding.tvAuthor.text = getAuthorName()
            val tagName = getTagName()
            if (tagName.isNullOrEmpty()) {
                mBinding.tvTag.visibility = View.GONE
            } else {
                mBinding.tvTag.visibility = View.VISIBLE
                mBinding.tvTag.text = tagName
            }
            mBinding.tvTime.text = niceDate
            // 缩略图
            if (envelopePic.isNullOrEmpty()) {
                mBinding.ivImg.visibility = View.GONE
            } else {
                mBinding.ivImg.visibility = View.VISIBLE
            }
            mBinding.tvTitle.text = Html.fromHtml(title)
            val chapter = getChapter()
            if (chapter.isNullOrEmpty()) {
                mBinding.tvChapterName.visibility = View.GONE
            } else {
                mBinding.tvChapterName.visibility = View.VISIBLE
                mBinding.tvChapterName.text = chapter
            }


        }
    }
}