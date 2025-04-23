package com.rickandmortyorlando.orlando.features.characters


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.domain.models.characters.Character
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.app_navigation.AppNavigationRoutes
import com.rickandmortyorlando.orlando.components.ItemCharacter
import com.rickandmortyorlando.orlando.components.ToolbarConfiguration
import com.rickandmortyorlando.orlando.components.skeletons.CharacterSkeleton
import com.rickandmortyorlando.orlando.features.base.BaseComposeScreen
import com.rickandmortyorlando.orlando.features.extensions.LoadState
import com.rickandmortyorlando.orlando.features.extensions.LoadStateConfig
import kotlinx.coroutines.flow.flowOf


@Composable
fun CharacterRoute(navController: NavController) {
    val viewModel: CharacterViewModel = hiltViewModel()
    val characters = viewModel.characters.collectAsLazyPagingItems()
    BaseComposeScreen(
        toolbarConfiguration = ToolbarConfiguration(
            title = stringResource(R.string.characters),
            clickOnBackButton = { navController.navigateUp() },
            actions = {
                IconButton(
                    onClick = {
                        navController.navigate(AppNavigationRoutes.SearchCharactersRoute)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = Color.White
                    )
                }
            }
        )
    ) {
        CharactersScreen(
            characters = characters,
            clickOnItem = { characterId, characterName ->
                navController.navigate(
                    AppNavigationRoutes.CharactersDetailRoute(
                        id = characterId,
                        name = characterName
                    )
                )
            }
        )
    }
}

@Composable
fun CharactersScreen(
    characters: LazyPagingItems<Character>,
    clickOnItem: (characterId: Int, characterName: String) -> Unit
) {
    CharactersScreenContent(characters = characters, clickOnItem = clickOnItem)
}

@Composable
private fun CharactersScreenContent(
    characters: LazyPagingItems<Character>,
    clickOnItem: (characterId: Int, characterName: String) -> Unit
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
                    clickOnItem = { id, name -> clickOnItem(id, name) }
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


@Preview(showBackground = true)
@Composable
private fun CharactersScreenPreview() {
    val items = flowOf(
        PagingData.from(
            listOf(
                Character.mockCharacter(),
                Character.mockCharacter()
            ),
            sourceLoadStates =
                LoadStates(
                    refresh = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false),
                    prepend = LoadState.NotLoading(false)
                )
        )
    ).collectAsLazyPagingItems()
    CharactersScreen(
        characters = items,
        clickOnItem = { id, name -> }
    )
}
