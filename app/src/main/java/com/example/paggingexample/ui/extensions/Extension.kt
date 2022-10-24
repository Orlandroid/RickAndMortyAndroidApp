package com.example.paggingexample.ui.extensions

import android.content.Context
import android.webkit.ConsoleMessage
import android.widget.Toast

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}