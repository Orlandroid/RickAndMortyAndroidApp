package com.rickandmortyorlando.orlando.features.search

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.example.domain.models.characters.Character
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.databinding.FragmentSearchBinding
import com.rickandmortyorlando.orlando.features.base.BaseFragment
import com.rickandmortyorlando.orlando.features.characters.CharacterAdapter
import com.rickandmortyorlando.orlando.features.extensions.hideProgress
import com.rickandmortyorlando.orlando.features.extensions.showErrorApi
import com.rickandmortyorlando.orlando.features.extensions.showProgress
import com.rickandmortyorlando.orlando.features.filter_dialog.FilterDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchCharactersFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val adapter = CharacterAdapter(clickOnCharacter = { clickOnCharacter(it) })
    private val viewModel: SearchCharactersViewModel by viewModels()

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
            SearchCharactersFragmentDirections.navigationToCharacterDetail(
                character.id
            )
        )
    }


}