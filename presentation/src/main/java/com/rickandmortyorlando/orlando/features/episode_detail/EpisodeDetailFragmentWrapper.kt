package com.rickandmortyorlando.orlando.features.episode_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.components.ErrorScreen
import com.rickandmortyorlando.orlando.components.ToolbarConfiguration
import com.rickandmortyorlando.orlando.components.skeletons.EpisodeDetailSkeleton
import com.rickandmortyorlando.orlando.features.base.BaseComposeScreen
import com.rickandmortyorlando.orlando.features.extensions.changeToolbarTitle
import com.rickandmortyorlando.orlando.features.extensions.content
import com.rickandmortyorlando.orlando.features.extensions.openYoutubeApp
import com.rickandmortyorlando.orlando.state.BaseViewState

class EpisodeDetailFragmentWrapper : Fragment(R.layout.fragment_episode_detail) {


    private val args: EpisodeDetailFragmentWrapperArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return content {
            val viewModel: EpisodeDetailViewModel = hiltViewModel()
            LaunchedEffect(Unit) {
                viewModel.getEpisodeDetail(args.idEpisode.toString())
            }
            val state = viewModel.state.collectAsStateWithLifecycle()
            BaseComposeScreen(
                toolbarConfiguration = ToolbarConfiguration(
                    title = stringResource(R.string.episode_detail),//Todo Change to dynamic name
                    clickOnBackButton = { findNavController().navigateUp() }
                )
            ) {
                when (val currentState = state.value) {
                    is BaseViewState.Loading -> {
                        EpisodeDetailSkeleton()
                    }

                    is BaseViewState.Content -> {
                        EpisodeDetailScreen(
                            uiState = currentState.result,
                            clickOnCharacter = { characterId ->
                                findNavController().navigate(
                                    EpisodeDetailFragmentWrapperDirections.navigationToCharacterDetailWrapper(
                                        characterId
                                    )
                                )
                            },
                            clickOnWatch = { episodeQuery ->
                                requireContext().openYoutubeApp(episodeQuery)
                            }
                        )
                        changeToolbarTitle(currentState.result.episode.name)
                    }

                    is BaseViewState.Error -> {
                        ErrorScreen()
                    }
                }
            }
        }
    }


}