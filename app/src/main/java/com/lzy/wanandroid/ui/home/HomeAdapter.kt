package com.lzy.wanandroid.ui.home

import android.content.Context
import android.view.ViewGroup
import com.lzy.corebiz.httpservice.bean.ArticleBean
import com.lzy.libview.BaseAdapter
import com.lzy.wanandroid.databinding.ItemHomeArticleLayoutBinding

/**
 * Created by zhaoyang.li5 on 2022/4/13 9:21
 */
class HomeAdapter(
    context: Context, articleList: List<ArticleBean>, listener: OnItemClickListener<ArticleBean>
) : BaseAdapter<ArticleBean, ItemHomeArticleLayoutBinding, HomeViewHolder>(
    context, articleList, listener
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            ItemHomeArticleLayoutBinding.inflate(
                mLayoutInflater, parent, false
            )
        )
    }
}