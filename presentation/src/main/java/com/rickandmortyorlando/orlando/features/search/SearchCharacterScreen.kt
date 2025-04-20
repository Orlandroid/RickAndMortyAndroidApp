package com.rickandmortyorlando.orlando.features.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.domain.models.characters.Character
import com.example.domain.models.characters.SearchCharacter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.components.ToolbarConfiguration
import com.rickandmortyorlando.orlando.features.base.BaseComposeScreen
import com.rickandmortyorlando.orlando.features.characters.CharactersScreen
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun SearchCharacterScreen(
    uiState: SearchCharacter,
    characters: LazyPagingItems<Character>,
    events: (event: SearchCharacterEvents) -> Unit,
    onBack: () -> Unit,
    clickOnCharacter: (id: Int, name: String) -> Unit
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
            clickOnCharacter = clickOnCharacter
        )
    }
}


@Composable
private fun SearchCharacterScreenContent(
    characters: LazyPagingItems<Character>,
    events: (event: SearchCharacterEvents) -> Unit,
    name: String,
    clickOnCharacter: (id: Int, name: String) -> Unit
) {
    var refreshing by remember { mutableStateOf(false) }
    LaunchedEffect(refreshing) {
        if (refreshing) {
            delay(1.seconds)
            refreshing = false
        }
    }
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                value = name,
                onValueChange = {
                    events(SearchCharacterEvents.OnValueChange(it))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                keyboardActions = KeyboardActions(
                    onDone = {
                        events(SearchCharacterEvents.OnSendQuery)
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
                        events(SearchCharacterEvents.OnClearQuery)
                    },
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = "ShoppingCartIcon"
                )
            }
        }
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = refreshing),
            onRefresh = {
                events(SearchCharacterEvents.OnSendQuery)
                refreshing = true
            }
        ) {
            //Todo add error when we don,t get any result
            CharactersScreen(
                characters = characters,
                clickOnItem = clickOnCharacter
            )
        }
    }
}