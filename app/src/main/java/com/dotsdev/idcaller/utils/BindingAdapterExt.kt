package com.dotsdev.idcaller.utils

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.dotsdev.idcaller.R

@SuppressLint("CheckResult")
@BindingAdapter(
    value = ["imageUrl", "placeholder", "errorDrawable", "centerCrop", "fitCenter", "circleCrop", "roundedCorners", "diskCacheStrategy", "signatureKey"],
    requireAll = false
)
fun ImageView.loadImage(
    imageUrl: String? = null,
    placeHolder: Drawable? = null,
    errorDrawable: Drawable? = null,
    centerCrop: Boolean = false,
    fitCenter: Boolean = false,
    circleCrop: Boolean = false,
    roundedCorners: Boolean? = true,
    diskCacheStrategy: DiskCacheStrategy? = null,
    signatureKey: String? = null
) {
    if (imageUrl.isNullOrBlank()) {
        setImageDrawable(placeHolder)
        return
    }

    val requestOptions = RequestOptions().apply {
        diskCacheStrategy(diskCacheStrategy ?: DiskCacheStrategy.AUTOMATIC)
        placeholder(
            ContextCompat.getDrawable(
                context,
                R.drawable.ic_no_pictures
            )
        )
        error(
            ContextCompat.getDrawable(
                context,
                R.drawable.ic_no_pictures
            )
        )

        if (roundedCorners != false) transform(CenterCrop(), RoundedCorners(20))
        if (centerCrop) centerCrop()
        if (fitCenter) fitCenter()
        if (circleCrop) circleCrop()

        if (!signatureKey.isNullOrBlank()) {
            signature(ObjectKey(signatureKey))
        }
    }

    Glide.with(context).load(imageUrl).apply {
        transition(DrawableTransitionOptions.withCrossFade())
        apply(requestOptions)
    }.into(this)
}
