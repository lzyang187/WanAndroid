package com.lzy.libview

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Created by zhaoyang.li5 on 2022/4/4 7:58
 */
abstract class BaseAdapter<D, VB : ViewBinding, VH : BaseViewHolder<D, VB>>(
    protected val mContext: Context, protected var mDataList: List<D>
) : RecyclerView.Adapter<VH>() {

    protected val mLayoutInflater: LayoutInflater by lazy {
        LayoutInflater.from(mContext)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(position, mDataList[position])
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

}