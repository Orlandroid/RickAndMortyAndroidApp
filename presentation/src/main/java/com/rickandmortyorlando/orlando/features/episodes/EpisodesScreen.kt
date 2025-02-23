package com.rickandmortyorlando.orlando.features.episodes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.domain.models.episodes.Episode
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.components.skeletons.EpisodeSkeleton
import com.rickandmortyorlando.orlando.features.extensions.LoadState
import com.rickandmortyorlando.orlando.features.extensions.LoadStateConfig
import kotlinx.coroutines.flow.flowOf

@Composable
fun EpisodesScreen(
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
                EpisodeItem(
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

@Composable
private fun EpisodeItem(
    episode: Episode,
    clickOnItem: (episodeId: Int) -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable { clickOnItem(episode.id) }) {
        Row(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = episode.episode,
                    fontWeight = FontWeight.Bold
                )
                Text(modifier = Modifier.fillMaxWidth(), text = episode.name)
                Text(modifier = Modifier.fillMaxWidth(), text = episode.airDate)
            }
            Icon(
                modifier = Modifier.padding(end = 16.dp),
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null
            )
        }
        HorizontalDivider(
            modifier = Modifier
                .padding(top = 8.dp)
                .background(colorResource(R.color.gris))
        )
    }
}

@Composable
@Preview(showBackground = true)
fun EpisodeItemPreview(modifier: Modifier = Modifier) {
    EpisodeItem(
        episode = Episode(
            airDate = stringResource(R.string.december_2_2013),
            characters = emptyList(),
            created = "",
            episode = stringResource(R.string.s01e01),
            id = 1,
            name = stringResource(R.string.pilot),
            url = ""
        ),
        clickOnItem = {})
}


@Preview(showBackground = true)
@Composable
fun EpisodesScreenPreview() {
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
    EpisodesScreen(
        episodes = items,
        clickOnItem = {}
    )
}

