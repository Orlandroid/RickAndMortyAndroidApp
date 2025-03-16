package com.rickandmortyorlando.orlando.features.extensions

import android.view.View
import android.view.ViewGroup


fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}



fun View.click(click: () -> Unit) {
    setOnClickListener { click() }
}


fun View.setMargins(left: Int, top: Int, right: Int, bottom: Int) {
    if (this.layoutParams is ViewGroup.MarginLayoutParams) {
        val p = this.layoutParams as ViewGroup.MarginLayoutParams
        p.setMargins(left, top, right, bottom)
        this.requestLayout()
    }
}

