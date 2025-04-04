package com.rickandmortyorlando.orlando.features.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.fragment.findNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.databinding.FragmentSearchBinding
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
            var refreshing by remember { mutableStateOf(false) }
            LaunchedEffect(refreshing) {
                if (refreshing) {
                    delay(3000)
                    refreshing = false
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


    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true, toolbarTitle = getString(R.string.search)
    )

    override fun configSearchView() =
        MainActivity.SearchViewConfig(
            showSearchView = true,
            onQueryTextSubmit = {
                viewModel.searchCharacter.name = it
                viewModel.refreshCharactersSearchPagingSource()
            },
            showFilterIcon = true,
            clickOnFilterIcon = {
                clickOnFilterIcon()
            }
        )

    private fun clickOnFilterIcon() {
        val filterDialog = FilterDialogFragment(
            currentFilter = viewModel.searchCharacter,
            searchInfo = {
                viewModel.searchCharacter = it
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