package com.rickandmortyorlando.orlando.utils

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate

class ThemeUtils {

    companion object {
        var themeUtils = ThemeUtils()
    }

    private fun isDarkTheme(context: Context) = context.resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES

    fun isLightTheme(context: Context) = !isDarkTheme(context)

    fun setNightMode(nightMode: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (nightMode) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}