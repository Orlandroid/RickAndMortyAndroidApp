package com.rickandmortyorlando.orlando.data.preferences

import android.content.SharedPreferences
import javax.inject.Inject

class RickAndMortyPreferences @Inject constructor(sharedPreferences: SharedPreferences) :
    PreferencesManager(sharedPreferences) {

    companion object {
        const val IS_NIGHT_MODE = "isNigthMode"
    }

    fun saveIsNightMode(isNightMode: Boolean?) {
        savePreferenceKey(IS_NIGHT_MODE, isNightMode)
    }

    fun getIsNightMode(): Boolean {
        return preferences.getBoolean(IS_NIGHT_MODE, false)
    }


}