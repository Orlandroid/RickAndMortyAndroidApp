package com.rickandmortyorlando.orlando.data.preferences

import android.content.SharedPreferences
import javax.inject.Inject

open class PreferencesManager @Inject constructor(protected val preferences: SharedPreferences) {


    open fun savePreferenceKey(key: String, value: Any?) {
        when (value) {
            is String -> preferences.edit().putString(key, value).apply()
            is Boolean -> preferences.edit().putBoolean(key, value).apply()
            is Float -> preferences.edit().putFloat(key, value).apply()
            is Int -> preferences.edit().putInt(key, value).apply()
        }
    }

    open fun removePreferenceKey(key: String) {
        preferences.edit().remove(key).apply()
    }

}