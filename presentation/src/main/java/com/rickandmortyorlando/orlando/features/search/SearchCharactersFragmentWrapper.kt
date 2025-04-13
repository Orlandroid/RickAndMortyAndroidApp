package com.rickandmortyorlando.orlando.features.search

import android.os.Bundle
import android.util.Log
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
import androidx.compose.material.icons.rounded.ShoppingCart
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
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.domain.models.characters.Character
import com.example.domain.models.characters.SearchCharacter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.components.ToolbarConfiguration
import com.rickandmortyorlando.orlando.databinding.FragmentSearchBinding
import com.rickandmortyorlando.orlando.features.base.BaseComposeScreen
import com.rickandmortyorlando.orlando.features.base.BaseFragment
import com.rickandmortyorlando.orlando.features.characters.CharactersScreen
import com.rickandmortyorlando.orlando.features.extensions.content
import com.rickandmortyorlando.orlando.features.filter_dialog.FilterDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SearchCharactersFragmentWrapper :
    BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val viewModel: SearchCharactersViewModel by viewModels()

    override fun setUpUi() {

    }

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
                Content(characters = characters)
            }
        }
    }

    @Composable
    private fun Content(characters: LazyPagingItems<Character>) {
        var refreshing by remember { mutableStateOf(false) }
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()
        LaunchedEffect(refreshing) {
            if (refreshing) {
                delay(3000)
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
                        uiState.value.name = it
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            viewModel.refreshCharactersSearchPagingSource()
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
                            uiState.value.name = ""
                        },
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = "ShoppingCartIcon"
                    )
                }
            }
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = refreshing),
                onRefresh = {
                    viewModel.refreshCharactersSearchPagingSource()
                    refreshing = true
                }
            ) {
                CharactersScreen(characters = characters, clickOnItem = ::clickOnCharacter)
            }
        }
    }

    override fun configSearchView() =
        MainActivity.SearchViewConfig(
            showSearchView = true,
            onQueryTextSubmit = {
//                viewModel.searchCharacter.name = it
                viewModel.refreshCharactersSearchPagingSource()
            },
            showFilterIcon = true,
            clickOnFilterIcon = {
                clickOnFilterIcon()
            }
        )

    private fun clickOnFilterIcon() {
        val filterDialog = FilterDialogFragment(
            currentFilter = SearchCharacter(),
            searchInfo = {
//                viewModel.searchCharacter = it
                viewModel.refreshCharactersSearchPagingSource()
            }
        )
        activity?.let { filterDialog.show(it.supportFragmentManager, "alertMessage") }
    }


    private fun clickOnCharacter(characterId: Int) {
        findNavController().navigate(
            SearchCharactersFragmentWrapperDirections.navigationToCharacterDetailWrapper(
                characterId
            )
        )
    }


}