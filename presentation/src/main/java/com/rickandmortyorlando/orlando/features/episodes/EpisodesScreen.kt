package com.rickandmortyorlando.orlando.features.episodes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.data.model.episode.EpisodeData
import com.example.domain.models.characters.Character
import com.example.domain.models.episodes.Episode
import com.rickandmortyorlando.orlando.components.ItemCharacter
import com.rickandmortyorlando.orlando.features.extensions.LoadState
import kotlinx.coroutines.flow.flowOf

@Composable
fun EpisodesScreen(
    episodes: LazyPagingItems<Episode>,
    clickOnItem: (Episode) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        //Todo add skeletons when we are in the first page
        items(
            count = episodes.itemCount,
            key = episodes.itemKey { it.id }
        ) { index ->
//            characters[index]?.let { character ->
//                ItemCharacter(
//                    modifier = Modifier.fillMaxWidth(),
//                    character = character,
//                    clickOnItem = clickOnItem
//                )
//            }
        }
        item {
            episodes.LoadState(Modifier.fillParentMaxSize())
        }
    }
}

//
//@Preview(showBackground = true)
//@Composable
//fun EpisodesScreenPreview() {
//    val mockEpisode = Character(
//        id = 0,
//        image = "",
//        name = "",
//        status = "",
//        species = "",
//        gender = "",
//        urlLocation = "",
//        episode = emptyList()
//    )
//    val items = flowOf(
//        PagingData.from(
//            listOf(
//                mockEpisode,
//                mockEpisode
//            )
//        )
//    ).collectAsLazyPagingItems()
//    EpisodesScreen(
//        characters = items,
//        clickOnItem = {}
//    )
//}

