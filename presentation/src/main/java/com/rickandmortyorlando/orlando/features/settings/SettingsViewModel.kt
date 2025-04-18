package com.rickandmortyorlando.orlando.features.settings

import androidx.lifecycle.ViewModel
import com.example.data.preferences.RickAndMortyPreferences
import com.rickandmortyorlando.orlando.utils.ThemeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


sealed class SettingsEvents {
    data class OnToggle(val isEnable: Boolean) : SettingsEvents()
}

data class SettingsUiState(val isNightModeEnable: Boolean = false)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val rickAndMortyPreferences: RickAndMortyPreferences
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(SettingsUiState().copy(isNightModeEnable = isNightMode()))
    val uiState = _uiState.asStateFlow()

    fun handleEvents(event: SettingsEvents) {
        when (event) {
            is SettingsEvents.OnToggle -> {
                changeTheme(event.isEnable)
                _uiState.update { it.copy(isNightModeEnable = event.isEnable) }
            }
        }
    }

    private fun changeTheme(isNightMode: Boolean) {
        rickAndMortyPreferences.saveIsNightMode(isNightMode)
        ThemeUtils.themeUtils.setNightMode(isNightMode)
    }

    private fun isNightMode() = rickAndMortyPreferences.getIsNightMode()
}