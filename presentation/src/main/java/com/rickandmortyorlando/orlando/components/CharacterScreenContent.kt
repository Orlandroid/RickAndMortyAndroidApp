package com.rickandmortyorlando.orlando.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.example.domain.models.characters.Character
import com.rickandmortyorlando.orlando.components.skeletons.CharacterSkeleton
import com.rickandmortyorlando.orlando.features.extensions.LoadState
import com.rickandmortyorlando.orlando.features.extensions.LoadStateConfig

@Composable
fun CharactersScreenContent(
    characters: LazyPagingItems<Character>,
    clickOnItem: (characterId: Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(
            count = characters.itemCount,
            key = characters.itemKey { it.id }
        ) { index ->
            characters[index]?.let { character ->
                ItemCharacter(
                    modifier = Modifier.fillMaxWidth(),
                    character = character,
                    clickOnItem = { id -> clickOnItem(id) }
                )
            }
        }
        item {
            characters.LoadState(
                modifier = Modifier.fillParentMaxSize(),
                config = LoadStateConfig(
                    initialLoading = {
                        for (i in 0..15) {
                            CharacterSkeleton()
                        }
                    }
                )
            )
        }
    }
}