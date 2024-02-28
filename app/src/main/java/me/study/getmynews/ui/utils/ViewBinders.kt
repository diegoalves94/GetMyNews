package me.study.getmynews.ui.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import me.study.getmynews.R

@BindingAdapter("srcUrl")
fun ImageView.bindSrcUrl(
    url: String?
) {
    url?.let {
        Glide.with(this)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .into(this)
    }
}
