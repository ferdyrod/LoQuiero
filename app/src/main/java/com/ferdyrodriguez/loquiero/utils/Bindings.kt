package com.ferdyrodriguez.loquiero.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.ferdyrodriguez.loquiero.extensions.loadImage
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File


@BindingAdapter("load_image")
fun setLoadImage(view: ImageView, file: File?){
    view.loadImage(file)
}

@BindingAdapter("load_image")
fun setLoadImage(view: ImageView, url: String?){
    view.loadImage(url)
}

@BindingAdapter(value = ["load_locally", "load_image"], requireAll = true)
fun setImage(view: CircleImageView, url: String?, file: File?){
    if(file != null)
        view.loadImage(file)
    else
        view.loadImage(url)
}

@BindingAdapter("price")
fun setPrice(view: TextView, price: Int){
    if (price <= 0){
        view.text = "0,00"
    } else {
        val formattedPrice = StringBuilder(price.toString())
        formattedPrice.insert(formattedPrice.length-2, ",")
        view.text = formattedPrice
    }
}
