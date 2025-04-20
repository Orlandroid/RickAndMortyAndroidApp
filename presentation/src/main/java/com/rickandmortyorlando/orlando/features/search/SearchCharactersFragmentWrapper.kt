package com.rickandmortyorlando.orlando.features.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.domain.models.characters.Character
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.components.ToolbarConfiguration
import com.rickandmortyorlando.orlando.features.base.BaseComposeScreen
import com.rickandmortyorlando.orlando.features.characters.CharactersScreen
import com.rickandmortyorlando.orlando.features.extensions.content
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@AndroidEntryPoint
class SearchCharactersFragmentWrapper : Fragment(R.layout.fragment_search) {

    private val viewModel: SearchCharactersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return content {
            val viewModel: SearchCharactersViewModel = hiltViewModel()
            val characters = viewModel.getCharactersSearchPagingSource.collectAsLazyPagingItems()
            BaseComposeScreen(
                toolbarConfiguration = ToolbarConfiguration(
                    title = stringResource(R.string.search),
                    clickOnBackButton = {
                        findNavController().navigateUp()
                    }
                )
            ) {
                Content(characters = characters, events = viewModel::handleEvents)
            }
        }
    }

    @Composable
    private fun Content(
        characters: LazyPagingItems<Character>,
        events: (event: SearchCharacterEvents) -> Unit
    ) {
        var refreshing by remember { mutableStateOf(false) }
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()
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
                    value = uiState.value.name,
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
                    visible = uiState.value.name.isNotEmpty()
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
                    clickOnItem = ::clickOnCharacter
                )
            }
        }
    }

    private fun clickOnCharacter(characterId: Int, name: String) {
        findNavController().navigate(
            SearchCharactersFragmentWrapperDirections.navigationToCharacterDetailWrapper(
                characterId,
                name
            )
        )
    }


}