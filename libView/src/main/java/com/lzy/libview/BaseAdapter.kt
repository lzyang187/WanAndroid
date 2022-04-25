package com.lzy.libview

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Created by zhaoyang.li5 on 2022/4/4 7:58
 */
abstract class BaseAdapter<D, VB : ViewBinding, VH : BaseViewHolder<D, VB>>(
    protected val mContext: Context,
    protected var mDataList: List<D>,
    protected val mItemClickListener: OnItemClickListener<D>? = null
) : RecyclerView.Adapter<VH>() {

    interface OnItemClickListener<D> {
        fun onItemClick(position: Int, data: D?)
    }

    protected val mLayoutInflater: LayoutInflater by lazy {
        LayoutInflater.from(mContext)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(position, mDataList[position])
        holder.itemView.setOnClickListener {
            mItemClickListener?.onItemClick(position, mDataList[position])
        }
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

}