package io.github.janbarari.foodapp.ui.common.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class GlideImageLoader : IImageLoader {

    override fun bind(imageView: ImageView, url: String?) {
        url?.let {
            Glide.with(imageView.context).load(url).apply(RequestOptions().dontTransform())
                .into(imageView)
        }
    }

}