package com.rickandmortyorlando.orlando.features.many_episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.components.ErrorScreen
import com.rickandmortyorlando.orlando.components.ItemEpisode
import com.rickandmortyorlando.orlando.components.ToolbarConfiguration
import com.rickandmortyorlando.orlando.components.skeletons.EpisodeSkeleton
import com.rickandmortyorlando.orlando.features.base.BaseComposeScreen
import com.rickandmortyorlando.orlando.features.extensions.content
import com.rickandmortyorlando.orlando.state.BaseViewState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ManyEpisodesFragmentWrapper : Fragment(R.layout.fragment_episodes) {

    private val args: ManyEpisodesFragmentWrapperArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return content {
            val viewModel: ManyEpisodesViewModel = hiltViewModel()
            LaunchedEffect(Unit) {
                viewModel.getEpisodes(args.idsEpisodes)
            }
            val state = viewModel.state.collectAsStateWithLifecycle()
            BaseComposeScreen(
                toolbarConfiguration = ToolbarConfiguration(
                    title = stringResource(R.string.episodes),
                    clickOnBackButton = { findNavController().navigateUp() })
            ) {
                when (val currentState = state.value) {
                    is BaseViewState.Loading -> {
                        Column {
                            for (i in 0..15) {
                                EpisodeSkeleton()
                            }
                        }
                    }

                    is BaseViewState.Content -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(currentState.result) { episode ->
                                ItemEpisode(
                                    episode = episode,
                                    clickOnItem = { episodesId ->
                                        findNavController().navigate(
                                            ManyEpisodesFragmentWrapperDirections.navigationToEpisodeDetailWrapper(
                                                episodesId
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    }

                    is BaseViewState.Error -> {
                        ErrorScreen()
                    }
                }
            }
        }
    }

}