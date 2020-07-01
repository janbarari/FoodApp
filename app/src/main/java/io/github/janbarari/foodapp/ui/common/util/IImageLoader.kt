package io.github.janbarari.foodapp.ui.common.util

import android.widget.ImageView

interface IImageLoader {

    fun bind(imageView: ImageView, url: String?)

}