package com.rickandmortyorlando.orlando.ui.search

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.example.domain.models.remote.character.Character
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.databinding.FragmentSearchBinding
import com.rickandmortyorlando.orlando.ui.base.BaseFragment
import com.rickandmortyorlando.orlando.ui.characters.CharacterAdapter
import com.rickandmortyorlando.orlando.ui.characters.CharacterViewModel
import com.rickandmortyorlando.orlando.ui.extensions.hideProgress
import com.rickandmortyorlando.orlando.ui.extensions.showErrorApi
import com.rickandmortyorlando.orlando.ui.extensions.showProgress
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val adapter = CharacterAdapter(clickOnCharacter = { clickOnCharacter(it) })
    private val viewModel: CharacterViewModel by viewModels()

    override fun setUpUi() = with(binding) {
        recyclerView.adapter = adapter
        swipRefresh.setOnRefreshListener {
            lifecycleScope.launch {
                adapter.submitData(PagingData.from(emptyList()))
                swipRefresh.isRefreshing = false
                viewModel.refreshCharactersSearchPagingSource()
            }
        }
        getCharacters()
        listenerAdapter()
    }

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = getString(R.string.search)
    )

    override fun configSearchView() = MainActivity.SearchViewConfig(
        showSearchView = true,
        onQueryTextSubmit = {
            viewModel.searchCharacter.name = it
            viewModel.refreshCharactersSearchPagingSource()
        },
        onMenuItemActionCollapse = {

        }
    )


    private fun getCharacters() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getCharactersSearchPagingSource.collectLatest { characters ->
                    adapter.submitData(lifecycle, characters)
                }
            }
        }
    }

    private fun listenerAdapter() {
        adapter.addLoadStateListener { loadState ->
            if (loadState.source.append is LoadState.Loading || loadState.source.refresh is LoadState.Loading) {
                showProgress()
            } else {
                hideProgress()
            }
            when (loadState.source.refresh) {
                is LoadState.Error -> {
                    showErrorApi(true)
                }

                else -> {}
            }
        }
    }
    

    private fun clickOnCharacter(character: Character) {
        findNavController().navigate(
            SearchFragmentDirections.navigationToCharacterDetail(
                character.id
            )
        )
    }


}