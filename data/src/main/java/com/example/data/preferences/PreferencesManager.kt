package com.example.data.preferences

import android.content.SharedPreferences
import javax.inject.Inject
import androidx.core.content.edit

open class PreferencesManager @Inject constructor(protected val preferences: SharedPreferences) {


    open fun savePreferenceKey(key: String, value: Any?) {
        when (value) {
            is String -> preferences.edit { putString(key, value) }
            is Boolean -> preferences.edit { putBoolean(key, value) }
            is Float -> preferences.edit { putFloat(key, value) }
            is Int -> preferences.edit { putInt(key, value) }
        }
    }

    open fun removePreferenceKey(key: String) {
        preferences.edit { remove(key) }
    }

}