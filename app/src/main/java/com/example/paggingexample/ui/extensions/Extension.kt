package com.example.paggingexample.ui.extensions

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.paggingexample.R

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun ImageView.loadImage(urlImage: String) {
    Glide.with(context).load(urlImage).placeholder(R.drawable.loading_animation)
        .transition(DrawableTransitionOptions.withCrossFade()).circleCrop().into(this)
}
