package com.rickandmortyorlando.orlando.features.extensions

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.ColorRes
import com.facebook.shimmer.ShimmerFrameLayout


fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun ImageView.changeDrawableColor(color: Int) {
    setColorFilter(resources.getColor(color))
}

fun View.click(click: () -> Unit) {
    setOnClickListener { click() }
}

fun View.getColor(@ColorRes color: Int): Int {
    return this.context.resources.getColor(color)
}

fun ShimmerFrameLayout.shouldShowSkeleton(isLoading: Boolean) {
    if (isLoading) {
        visible()
    } else {
        gone()
    }
}

fun View.getPackageName(): String {
    return context?.applicationContext?.packageName.toString()
}

fun View.setMargins(left: Int, top: Int, right: Int, bottom: Int) {
    if (this.layoutParams is ViewGroup.MarginLayoutParams) {
        val p = this.layoutParams as ViewGroup.MarginLayoutParams
        p.setMargins(left, top, right, bottom)
        this.requestLayout()
    }
}

