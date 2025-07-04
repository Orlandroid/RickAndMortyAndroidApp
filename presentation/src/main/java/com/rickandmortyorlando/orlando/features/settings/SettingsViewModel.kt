package com.rickandmortyorlando.orlando.features.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.preferences.RickAndMortyPreferences
import com.rickandmortyorlando.orlando.utils.ThemeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class SettingsEvents {
    data class OnToggle(val isEnable: Boolean) : SettingsEvents()
    data class OnBack(val isEnable: Boolean) : SettingsEvents()
}

sealed class SettingsEffect {
    data object OnBack : SettingsEffect()
}

data class SettingsUiState(val isNightModeEnable: Boolean = false)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val rickAndMortyPreferences: RickAndMortyPreferences
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(SettingsUiState().copy(isNightModeEnable = isNightMode()))
    val uiState = _uiState.asStateFlow()


    private val _effects: Channel<SettingsEffect> = Channel()

    fun handleEvents(event: SettingsEvents) {
        when (event) {
            is SettingsEvents.OnToggle -> {
                changeTheme(event.isEnable)
                _uiState.update { it.copy(isNightModeEnable = event.isEnable) }
            }

            is SettingsEvents.OnBack -> {
                viewModelScope.launch {
                    _effects.send(SettingsEffect.OnBack)
                }
            }
        }
    }

    private fun changeTheme(isNightMode: Boolean) {
        rickAndMortyPreferences.saveIsNightMode(isNightMode)
        ThemeUtils.themeUtils.setNightMode(isNightMode)
    }

    private fun isNightMode() = rickAndMortyPreferences.getIsNightMode()
}