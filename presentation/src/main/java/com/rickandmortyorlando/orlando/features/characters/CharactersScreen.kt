package com.rickandmortyorlando.orlando.features.characters


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.domain.models.characters.Character
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.app_navigation.AppNavigationRoutes
import com.rickandmortyorlando.orlando.app_navigation.AppNavigationRoutes.CharactersDetailRoute
import com.rickandmortyorlando.orlando.components.CharactersScreenContent
import com.rickandmortyorlando.orlando.components.ToolbarConfiguration
import com.rickandmortyorlando.orlando.features.base.BaseComposeScreen
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf


@Composable
fun CharacterRoute(navController: NavController) {
    val viewModel: CharacterViewModel = hiltViewModel()
    val characters = viewModel.characters.collectAsLazyPagingItems()
    LaunchedEffect(Unit) {
        viewModel.effects.collectLatest {
            when (it) {
                is CharacterEffects.NavigateToCharacterDetail -> {
                    navController.navigate(
                        CharactersDetailRoute(
                            id = it.characterId
                        )
                    )
                }

                CharacterEffects.NavigateToSearchScreen -> {
                    navController.navigate(AppNavigationRoutes.SearchCharactersRoute)
                }
            }
        }
    }
    BaseComposeScreen(
        toolbarConfiguration = ToolbarConfiguration(
            title = stringResource(R.string.characters),
            clickOnBackButton = { navController.navigateUp() },
            actions = {
                IconButton(
                    onClick = {
                        viewModel.handleEvent(CharacterEvents.OnSearchClicked)
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
            clickOnItem = { characterId ->
                viewModel.handleEvent(CharacterEvents.OnCharacterClicked(characterId = characterId))
            }
        )
    }
}

@Composable
fun CharactersScreen(
    characters: LazyPagingItems<Character>,
    clickOnItem: (characterId: Int) -> Unit
) {
    CharactersScreenContent(characters = characters, clickOnItem = clickOnItem)
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
        clickOnItem = { id -> }
    )
}
