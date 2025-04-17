package com.rickandmortyorlando.orlando.features.extensions

import android.view.View


fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.click(click: () -> Unit) {
    setOnClickListener { click() }
}

