package com.lzy.libview.banner

import com.lzy.libview.BaseViewHolder
import com.lzy.libview.databinding.LibViewBannerItemLayoutBinding

/**
 * Created by zhaoyang.li5 on 2022/4/25 19:46
 */
class BannerViewHolder(binding: LibViewBannerItemLayoutBinding) :
    BaseViewHolder<IBannerData, LibViewBannerItemLayoutBinding>(binding) {
    override fun bind(position: Int, data: IBannerData?) {
        data?.bindImgUrl(mBinding.imageView)
    }
}