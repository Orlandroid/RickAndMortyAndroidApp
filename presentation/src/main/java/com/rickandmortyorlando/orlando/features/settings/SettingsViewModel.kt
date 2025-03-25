package com.rickandmortyorlando.orlando.features.settings

import androidx.lifecycle.ViewModel
import com.example.data.preferences.RickAndMortyPreferences
import com.rickandmortyorlando.orlando.utils.ThemeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val rickAndMortyPreferences: RickAndMortyPreferences) :
    ViewModel() {

    fun changeTheme(isNightMode: Boolean) {
        rickAndMortyPreferences.saveIsNightMode(isNightMode)
        ThemeUtils.themeUtils.setNightMode(isNightMode)
    }

    fun isNightMode() = rickAndMortyPreferences.getIsNightMode()
}