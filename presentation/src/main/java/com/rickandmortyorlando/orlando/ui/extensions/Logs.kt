package com.rickandmortyorlando.orlando.ui.extensions

import androidx.fragment.app.Fragment
import timber.log.Timber

fun Fragment.showLogW(error: String) {
    Timber.tag(getPackageName() + "--> Error").w(error)
}