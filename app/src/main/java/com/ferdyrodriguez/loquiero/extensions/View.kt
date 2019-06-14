package com.ferdyrodriguez.loquiero.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.File


fun ImageView.loadImage(file: File?) {
    Glide.with(this.context)
        .load(file)
        .centerCrop()
        .into(this)
}