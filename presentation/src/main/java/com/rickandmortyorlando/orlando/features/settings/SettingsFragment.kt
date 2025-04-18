package com.rickandmortyorlando.orlando.features.settings


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.components.ToolbarConfiguration
import com.rickandmortyorlando.orlando.features.base.BaseComposeScreen
import com.rickandmortyorlando.orlando.features.extensions.content
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return content {
            val viewModel: SettingsViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            BaseComposeScreen(
                toolbarConfiguration = ToolbarConfiguration(
                    title = stringResource(R.string.settings),
                    clickOnBackButton = { findNavController().navigateUp() })
            ) {
                SettingScreen(onEvents = viewModel::handleEvents, uiState = uiState)
            }
        }
    }


}