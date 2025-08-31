package com.rickandmortyorlando.orlando.features.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.domain.models.characters.Character
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.app_navigation.AppNavigationRoutes
import com.rickandmortyorlando.orlando.components.CharactersScreenContent
import com.rickandmortyorlando.orlando.components.ToolbarConfiguration
import com.rickandmortyorlando.orlando.features.base.BaseComposeScreen
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds


@Composable
fun SearchCharacterRoute(navController: NavController) {
    val viewModel: SearchCharactersViewModel = hiltViewModel()
    val characters = viewModel.getCharactersSearchPagingSource.collectAsLazyPagingItems()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    SearchCharacterScreen(
        onBack = { navController.navigateUp() },
        characters = characters,
        events = viewModel::handleEvents,
        uiState = uiState.value,
        clickOnCharacter = { id ->
            navController.navigate(
                AppNavigationRoutes.CharactersDetailRoute(id = id)
            )
        }
    )
}

@Composable
private fun SearchCharacterScreen(
    uiState: SearchCharacterUiState,
    characters: LazyPagingItems<Character>,
    events: (event: SearchCharacterEvents) -> Unit,
    onBack: () -> Unit,
    clickOnCharacter: (id: Int) -> Unit
) {
    BaseComposeScreen(
        toolbarConfiguration = ToolbarConfiguration(
            title = stringResource(R.string.search),
            clickOnBackButton = onBack
        )
    ) {
        SearchCharacterScreenContent(
            characters = characters,
            events = events,
            name = uiState.name,
            clickOnCharacter = clickOnCharacter,
            isRefreshing = uiState.isRefreshing
        )
    }
}


@Composable
private fun SearchCharacterScreenContent(
    characters: LazyPagingItems<Character>,
    events: (event: SearchCharacterEvents) -> Unit,
    name: String,
    isRefreshing: Boolean,
    clickOnCharacter: (id: Int) -> Unit,
) {
    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            delay(1.seconds)
            events(SearchCharacterEvents.OnSwipeRefresh(false))
        }
    }
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            InputSearch(name = name, onEvents = events)
        }
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
            onRefresh = {
                events(SearchCharacterEvents.OnSendQuery)
                events(SearchCharacterEvents.OnSwipeRefresh(true))
            }
        ) {
            //Todo add error when we don,t get any result
            CharactersScreenContent(
                characters = characters,
                clickOnItem = clickOnCharacter
            )
        }
    }
}

@Composable
private fun RowScope.InputSearch(
    name: String,
    onEvents: (event: SearchCharacterEvents) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .weight(1f)
            .padding(8.dp),
        value = name,
        onValueChange = {
            onEvents(SearchCharacterEvents.OnValueChange(it))
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        keyboardActions = KeyboardActions(
            onDone = {
                onEvents(SearchCharacterEvents.OnSendQuery)
            }
        ),
        singleLine = true
    )
    AnimatedVisibility(
        modifier = Modifier.padding(end = 8.dp),
        visible = name.isNotEmpty()
    ) {
        Icon(
            modifier = Modifier.clickable {
                onEvents(SearchCharacterEvents.OnClearQuery)
            },
            imageVector = Icons.Rounded.Delete,
            contentDescription = "ShoppingCartIcon"
        )
    }
}