package com.rickandmortyorlando.orlando.features.extensions

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.net.toUri


fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}


@SuppressLint("QueryPermissionsNeeded")
fun Context.openYoutubeApp(search: String) {
    try {
        val searchUrl = "Rick and morty $search"
        val youtubeUrlSearch = "https://www.youtube.com/results?search_query="
        val fullUrl = "$youtubeUrlSearch$searchUrl"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = fullUrl.toUri()
        intent.setPackage("com.google.android.youtube")
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        showToast("App not found")
    }
}



