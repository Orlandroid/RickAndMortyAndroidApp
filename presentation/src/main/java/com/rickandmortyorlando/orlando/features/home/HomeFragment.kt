package com.rickandmortyorlando.orlando.features.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.features.base.BaseComposeScreen
import com.rickandmortyorlando.orlando.components.ToolbarConfiguration
import com.rickandmortyorlando.orlando.features.episodes.EpisodesViewModel
import com.rickandmortyorlando.orlando.features.extensions.content
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_menu) {

    private val viewModel: EpisodesViewModel by navGraphViewModels(R.id.main_graph) {
        defaultViewModelProviderFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return content {
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
                HomeScreen(
                    clickOnCharacters = {
                        viewModel.comesFromEpisodesMainMenu = false
                        val action =
                            HomeFragmentDirections.actionMenuFragmentToCharacterFragmentWrapper()
                        findNavController().navigate(action)
                    },
                    clickOnEpisodes = {
                        viewModel.comesFromEpisodesMainMenu = true
                        findNavController().navigate(HomeFragmentDirections.actionMenuFragmentToEpisodesFragmentWrapper())
                    },
                    clickOnLocation = {
                        findNavController().navigate(HomeFragmentDirections.actionMenuFragmentToLocationsFragmentWrapper())
                    }
                )
            }
        }
    }


}