package com.rickandmortyorlando.orlando.features.settings

import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {
    
    data class SettingUiState(
        val isChecked: Boolean
    )
}