package com.ferdyrodriguez.loquiero.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.ferdyrodriguez.loquiero.R
import java.io.File


fun ImageView.loadImage(file: File?) {
    Glide.with(this.context)
        .load(file)
        .centerCrop()
        .into(this)
}

fun ImageView.loadImage(url: String?, isProfile: Boolean = false) {
    if(isProfile) {
        Glide.with(this.context)
            .load(url)
            .placeholder(this.context.getDrawable(R.drawable.ic_account_circle))
            .into(this)
    } else {
        Glide.with(this.context)
            .load(url)
            .centerCrop()
            .into(this)
    }
}

fun ViewGroup.inflater(): LayoutInflater =
    LayoutInflater.from(context)

fun View.visible() { this.visibility = View.VISIBLE }

fun View.invisible() { this.visibility = View.INVISIBLE }

fun View.gone() { this.visibility = View.GONE }

fun View.isVisible() = this.visibility == View.VISIBLE