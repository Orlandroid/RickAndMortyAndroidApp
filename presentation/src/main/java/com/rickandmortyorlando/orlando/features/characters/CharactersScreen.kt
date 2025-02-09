package com.rickandmortyorlando.orlando.features.characters


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.domain.models.characters.Character
import com.rickandmortyorlando.orlando.components.ItemCharacter
import com.rickandmortyorlando.orlando.features.extensions.LoadState
import kotlinx.coroutines.flow.flowOf

@Composable
fun CharactersScreen(
    characters: LazyPagingItems<Character>,
    clickOnItem: (Character) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        //Todo add skeletons when we are in the first page
        items(
            count = characters.itemCount,
            key = characters.itemKey { it.id }
        ) { index ->
            characters[index]?.let { character ->
                ItemCharacter(
                    modifier = Modifier.fillMaxWidth(),
                    character = character,
                    clickOnItem = clickOnItem
                )
            }
        }
        item {
            characters.LoadState(Modifier.fillParentMaxSize())
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CharactersScreenPreview() {
    val mockCharacter = Character(
        id = 0,
        image = "",
        name = "",
        status = "",
        species = "",
        gender = "",
        urlLocation = "",
        episode = emptyList()
    )
    val items = flowOf(
        PagingData.from(
            listOf(
                mockCharacter,
                mockCharacter
            ),
            sourceLoadStates =
            LoadStates(
                refresh = LoadState.NotLoading(false),
                append = LoadState.NotLoading(false),
                prepend = LoadState.NotLoading(false)
            ),
        )
    ).collectAsLazyPagingItems()
    CharactersScreen(
        characters = items,
        clickOnItem = {}
    )
}
