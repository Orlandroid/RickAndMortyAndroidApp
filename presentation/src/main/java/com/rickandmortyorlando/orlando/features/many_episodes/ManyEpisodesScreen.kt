package com.rickandmortyorlando.orlando.features.many_episodes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.domain.models.episodes.Episode
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.components.ErrorScreen
import com.rickandmortyorlando.orlando.components.ItemEpisode
import com.rickandmortyorlando.orlando.components.ToolbarConfiguration
import com.rickandmortyorlando.orlando.components.skeletons.EpisodeSkeleton
import com.rickandmortyorlando.orlando.features.base.BaseComposeScreen
import com.rickandmortyorlando.orlando.state.BaseViewState


@Composable
fun ManyEpisodesScreen(
    viewState: BaseViewState<List<Episode>>,
    onNavigateBack: () -> Unit
) {
    BaseComposeScreen(
        toolbarConfiguration = ToolbarConfiguration(
            title = stringResource(R.string.episodes),
            clickOnBackButton = onNavigateBack
        )
    ) {
        when (viewState) {
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
                    items(viewState.result) { episode ->
                        ItemEpisode(
                            episode = episode,
                            clickOnItem = { episodesId ->
//                                findNavController().navigate(
//                                    ManyEpisodesFragmentWrapperDirections.navigationToEpisodeDetailWrapper(
//                                        episodesId
//                                    )
//                                )
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