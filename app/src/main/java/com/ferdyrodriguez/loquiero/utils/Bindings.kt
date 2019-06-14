package com.ferdyrodriguez.loquiero.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.ferdyrodriguez.loquiero.extensions.loadImage
import pl.aprilapps.easyphotopicker.MediaFile


@BindingAdapter("load_image")
fun setLoadImage(view: ImageView, mediaFile: MediaFile?){
    view.loadImage(mediaFile?.file)
}
