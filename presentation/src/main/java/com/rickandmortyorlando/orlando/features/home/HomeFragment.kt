package com.rickandmortyorlando.orlando.features.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.fragment.findNavController
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.components.ToolbarConfiguration
import com.rickandmortyorlando.orlando.features.base.BaseComposeScreen
import com.rickandmortyorlando.orlando.features.extensions.content
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_menu) {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return content {
            val viewModel: HomeViewModel = hiltViewModel()
            BaseComposeScreen(
                toolbarConfiguration = ToolbarConfiguration(
                    showBackIcon = false,
                    title = stringResource(R.string.home),
                    actions = {
                        IconButton(
                            onClick = {
                                findNavController().navigate(HomeFragmentDirections.actionMenuFragmentToSettingsFragment())
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = "Settings",
                                tint = Color.White
                            )
                        }
                    }
                )
            ) {
                val android = viewModel.effects
                LaunchedEffect(viewModel) {
                    viewModel.effects.collectLatest {
                        when (it) {
                            HomeEffects.NavigateToCharacters -> {
                                findNavController().navigate(HomeFragmentDirections.actionMenuFragmentToCharacterFragmentWrapper())
                            }

                            HomeEffects.NavigateToEpisodes -> {
                                findNavController().navigate(HomeFragmentDirections.actionMenuFragmentToEpisodesFragmentWrapper())
                            }

                            HomeEffects.NavigateToLocations -> {
                                findNavController().navigate(HomeFragmentDirections.actionMenuFragmentToLocationsFragmentWrapper())
                            }

                            HomeEffects.NavigateToSettings -> {}
                        }
                    }
                }
                HomeScreen(onEvents = viewModel::onEvents)
            }
        }
    }


}