package com.rickandmortyorlando.orlando.features.episodes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.domain.models.episodes.Episode
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.app_navigation.AppNavigationRoutes
import com.rickandmortyorlando.orlando.components.ItemEpisode
import com.rickandmortyorlando.orlando.components.ToolbarConfiguration
import com.rickandmortyorlando.orlando.components.skeletons.EpisodeSkeleton
import com.rickandmortyorlando.orlando.features.base.BaseComposeScreen
import com.rickandmortyorlando.orlando.features.extensions.LoadState
import com.rickandmortyorlando.orlando.features.extensions.LoadStateConfig
import kotlinx.coroutines.flow.flowOf

@Composable
fun EpisodesRoute(navController: NavController) {
    val viewModel: EpisodesViewModel = hiltViewModel()
    val episodes = viewModel.episodes.collectAsLazyPagingItems()
    EpisodesScreen(
        episodes = episodes,
        clickOnItem = {
            navController.navigate(AppNavigationRoutes.EpisodesDetailRoute(it))
        },
        clickOnBackButton = { navController.navigateUp() }
    )
}

@Composable
fun EpisodesScreen(
    episodes: LazyPagingItems<Episode>,
    clickOnItem: (episodeId: Int) -> Unit,
    clickOnBackButton: () -> Unit
) {
    BaseComposeScreen(
        toolbarConfiguration = ToolbarConfiguration(
            title = stringResource(R.string.episodes),
            clickOnBackButton = clickOnBackButton
        )
    ) {
        EpisodesScreenContent(episodes = episodes, clickOnItem = clickOnItem)
    }
}

@Composable
private fun EpisodesScreenContent(
    episodes: LazyPagingItems<Episode>,
    clickOnItem: (episodeId: Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(
            count = episodes.itemCount,
            key = episodes.itemKey { it.id }
        ) { index ->
            episodes[index]?.let { episode ->
                ItemEpisode(
                    episode = episode,
                    clickOnItem = clickOnItem
                )
            }
        }
        item {
            episodes.LoadState(
                Modifier.fillParentMaxSize(), config = LoadStateConfig(
                    initialLoading = {
                        for (i in 0..15) {
                            EpisodeSkeleton()
                        }
                    }
                )
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun EpisodesScreenPreview() {
    val mockEpisode = Episode(
        id = 0,
        airDate = "December 2, 2013",
        characters = emptyList(),
        created = "",
        episode = "",
        name = "Pilote",
        url = ""
    )
    val items = flowOf(
        PagingData.from(
            listOf(
                mockEpisode,
                mockEpisode
            )
        )
    ).collectAsLazyPagingItems()
    EpisodesScreenContent(
        episodes = items,
        clickOnItem = {}
    )
}

