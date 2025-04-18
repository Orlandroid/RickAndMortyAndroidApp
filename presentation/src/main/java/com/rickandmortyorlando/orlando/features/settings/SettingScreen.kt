package com.rickandmortyorlando.orlando.features.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.theme.AlwaysWhite
import com.rickandmortyorlando.orlando.theme.Background

@Composable
fun SettingScreen(
    onEvents: (event: SettingsEvents) -> Unit,
    uiState: SettingsUiState
) {
    Column(
        Modifier
            .background(Background)
            .fillMaxSize()
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
            colors = CardDefaults.cardColors(containerColor = AlwaysWhite)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    text = stringResource(R.string.change_theme)
                )
                Switch(
                    modifier = Modifier.weight(1f),
                    checked = uiState.isNightModeEnable,
                    onCheckedChange = {
                        onEvents(SettingsEvents.OnToggle(it))
                    }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun SettingsScreenEnablePreview(modifier: Modifier = Modifier) {
    SettingScreen(onEvents = {}, uiState = SettingsUiState(isNightModeEnable = true))
}

@Composable
@Preview(showBackground = true)
private fun SettingsScreenDisablePreview(modifier: Modifier = Modifier) {
    SettingScreen(onEvents = {}, uiState = SettingsUiState())
}