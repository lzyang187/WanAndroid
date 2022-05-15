package com.lzy.wanandroid.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lzy.corebiz.httpservice.bean.ArticleBean
import com.lzy.corebiz.httpservice.bean.BannerBean
import com.lzy.libview.BaseAdapter
import com.lzy.libview.banner.BannerView
import com.lzy.libview.banner.IBannerData
import com.lzy.wanandroid.R
import com.lzy.wanandroid.collect.BaseCollectViewModel
import com.lzy.wanandroid.databinding.ItemHomeArticleLayoutBinding

/**
 * Created by zhaoyang.li5 on 2022/4/13 9:21
 */
class HomeAdapter(
    private val mContext: Context,
    private var mArticleList: List<ArticleBean>,
    private val mListener: BaseAdapter.OnItemClickListener<ArticleBean>,
    private val mViewModel: BaseCollectViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mLayoutInflater: LayoutInflater by lazy {
        LayoutInflater.from(mContext)
    }

    private lateinit var mHeaderView: BannerView
    private var mBannerList: List<BannerBean>? = null
    private var mBannerItemClickListener: BaseAdapter.OnItemClickListener<IBannerData>? = null
    private var mSupportHeader = true
    private var mHeaderCount = 1

    fun setSupportHeader(support: Boolean) {
        mSupportHeader = support
        if (!mSupportHeader) {
            mHeaderCount = 0
        }
    }

    fun setBannerList(
        bannerList: List<BannerBean>,
        itemClickListener: BaseAdapter.OnItemClickListener<IBannerData>
    ) {
        mBannerItemClickListener = itemClickListener
        mBannerList = bannerList
        if (::mHeaderView.isInitialized) {
            mHeaderView.setBannerData(bannerList, mBannerItemClickListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HEADER) {
            if (!::mHeaderView.isInitialized) {
                mHeaderView = mLayoutInflater.inflate(
                    R.layout.banner_view_layout, parent, false
                ) as BannerView
                mBannerList?.let {
                    mHeaderView.setBannerData(it, mBannerItemClickListener)
                }
            }
            object : RecyclerView.ViewHolder(
                mHeaderView
            ) {

            }
        } else {
            HomeViewHolder(
                ItemHomeArticleLayoutBinding.inflate(
                    mLayoutInflater, parent, false
                ), mViewModel
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewType = getItemViewType(position)
        if (itemViewType == VIEW_TYPE_HEADER) {
            // ignore
        } else {
            (holder as HomeViewHolder).bind(
                position, mArticleList[position - mHeaderCount]
            )
            holder.itemView.setOnClickListener {
                mListener.onItemClick(
                    position, mArticleList[position - mHeaderCount]
                )
            }
        }
    }

    override fun getItemCount() = mArticleList.size + mHeaderCount

    override fun getItemViewType(position: Int): Int {
        if (mSupportHeader && position == 0) {
            return VIEW_TYPE_HEADER
        }
        return VIEW_TYPE_ARTICLE
    }

    companion object {
        const val VIEW_TYPE_HEADER = 1
        const val VIEW_TYPE_ARTICLE = 2
    }
}