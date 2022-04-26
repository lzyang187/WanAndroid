package com.lzy.corebiz.httpservice.bean

import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.lzy.libbasefunction.R
import com.lzy.libbasefunction.glide.GlideHelper
import com.lzy.libview.banner.IBannerData
import java.io.Serializable
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

/**
 * Created by zhaoyang.li5 on 2022/4/25 17:42
 */
data class BannerBean(
    val desc: String?, // 我们支持订阅啦~
    val id: Int?, // 30
    val imagePath: String?, // https://www.wanandroid.com/blogimgs/42da12d8-de56-4439-b40c-eab66c227a4b.png
    val isVisible: Int?, // 1
    val order: Int?, // 0
    val title: String?, // 我们支持订阅啦~
    val type: Int?, // 0
    val url: String? // https://www.wanandroid.com/blog/show/3352
) : IBannerData, Serializable {
    override fun bindImgUrl(iv: ImageView) {
        GlideHelper.load(
            Glide.with(iv), imagePath, iv, RequestOptions.bitmapTransform(
                MultiTransformation(
                    CenterCrop(), RoundedCornersTransformation(
                        0, 0, RoundedCornersTransformation.CornerType.ALL
                    )
                )
            ).placeholder(
                ContextCompat.getDrawable(
                    iv.context, R.drawable.lib_basefunction_shape_image_place_holder
                )
            ).error(
                ContextCompat.getDrawable(
                    iv.context, R.drawable.lib_basefunction_shape_image_place_holder
                )
            )
        )
    }
}