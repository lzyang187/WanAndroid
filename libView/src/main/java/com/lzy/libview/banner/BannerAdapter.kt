package com.lzy.libview.banner

import android.content.Context
import android.view.ViewGroup
import com.lzy.libview.BaseAdapter
import com.lzy.libview.databinding.LibViewBannerItemLayoutBinding

/**
 * Created by zhaoyang.li5 on 2022/4/25 19:42
 */
class BannerAdapter(
    context: Context,
    dataList: List<IBannerData>,
    itemClickListener: OnItemClickListener<IBannerData>?
) : BaseAdapter<IBannerData, LibViewBannerItemLayoutBinding, BannerViewHolder>(
    context, dataList, itemClickListener
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        return BannerViewHolder(
            LibViewBannerItemLayoutBinding.inflate(
                mLayoutInflater, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val realPos = position % mDataList.size
        holder.bind(realPos, mDataList[realPos])
        holder.itemView.setOnClickListener {
            mItemClickListener?.onItemClick(realPos, mDataList[realPos])
        }
    }

    override fun getItemCount(): Int {
        if (mDataList.isEmpty()) {
            return 0
        }
        return Int.MAX_VALUE
    }

}