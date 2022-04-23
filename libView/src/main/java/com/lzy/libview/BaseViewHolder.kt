package com.lzy.libview

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Created by zhaoyang.li5 on 2022/4/4 7:54
 */
abstract class BaseViewHolder<D, VB : ViewBinding>(protected val mBinding: VB) :
    RecyclerView.ViewHolder(mBinding.root) {

    abstract fun bind(position: Int, data: D?)
}