package com.example.paggingexample.ui.extensions

import android.content.Context
import android.graphics.PorterDuff
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.paggingexample.R

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun ImageView.loadImage(urlImage: String) {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.setTint(ContextCompat.getColor(context, R.color.primary))
    circularProgressDrawable.setColorFilter(
        ContextCompat.getColor(context, R.color.primary),
        PorterDuff.Mode.SRC_IN
    )
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()
    Glide.with(context).load(urlImage).placeholder(circularProgressDrawable).transition(DrawableTransitionOptions.withCrossFade()).circleCrop().into(this)
}
