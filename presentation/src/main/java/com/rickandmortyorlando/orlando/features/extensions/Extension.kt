package com.rickandmortyorlando.orlando.features.extensions

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rickandmortyorlando.orlando.R


fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun ImageView.loadImage(urlImage: String) {
    Glide.with(context).load(urlImage).error(R.drawable.rick_and_morty)
        .placeholder(R.drawable.loading_animation)
        .transition(DrawableTransitionOptions.withCrossFade()).circleCrop().into(this)
}

@SuppressLint("QueryPermissionsNeeded")
fun Context.openYoutubeApp(search: String) {
    try {
        val searchUrl = "Rick and morty $search"
        val youtubeUrlSearch = "https://www.youtube.com/results?search_query="
        val fullUrl = "$youtubeUrlSearch$searchUrl"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(fullUrl)
        intent.setPackage("com.google.android.youtube")
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        showToast("App not found")
    }
}



