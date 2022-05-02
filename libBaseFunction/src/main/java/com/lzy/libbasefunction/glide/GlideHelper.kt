package com.lzy.libbasefunction.glide

import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.lzy.libbasefunction.R
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

/**
 * Created by zhaoyang.li5 on 2022/4/24 11:01
 */
object GlideHelper {
    fun load(
        @NonNull requestManager: RequestManager,
        url: String?,
        @NonNull iv: ImageView,
        options: RequestOptions = getDefaultRequestOptions(iv)
    ) {
        requestManager.load(url).apply(options).into(iv)
    }

    private fun getDefaultRequestOptions(@NonNull iv: ImageView): RequestOptions {
        return RequestOptions.bitmapTransform(
            MultiTransformation(
                CenterCrop(), RoundedCornersTransformation(
                    10, 0, RoundedCornersTransformation.CornerType.ALL
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
    }

    fun getCircleRequestOptions(@NonNull iv: ImageView): RequestOptions {
        return RequestOptions.bitmapTransform(
            MultiTransformation(
                CenterCrop(), CircleCrop()
            )
        ).placeholder(
            ContextCompat.getDrawable(
                iv.context, R.drawable.lib_basefunction_ic_baseline_account_circle_24
            )
        ).error(
            ContextCompat.getDrawable(
                iv.context, R.drawable.lib_basefunction_ic_baseline_account_circle_24
            )
        )
    }

}