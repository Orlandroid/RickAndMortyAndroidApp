package com.rickandmortyorlando.orlando.ui.extensions

import android.util.Log
import androidx.fragment.app.Fragment

fun Fragment.showLogW(error: String) {
    Log.w("${getPackageName()}--> Error", error)
}