package com.rickandmortyorlando.orlando.ui.search

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.domain.models.local.SearchCharacter
import com.example.domain.models.remote.character.Character
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.databinding.FragmentSearchBinding
import com.rickandmortyorlando.orlando.ui.base.BaseFragment
import com.rickandmortyorlando.orlando.ui.characters.CharacterAdapter
import com.rickandmortyorlando.orlando.ui.characters.CharacterFragmentDirections
import com.rickandmortyorlando.orlando.ui.characters.CharacterViewModel
import com.rickandmortyorlando.orlando.ui.extensions.hideProgress
import com.rickandmortyorlando.orlando.ui.extensions.showErrorApi
import com.rickandmortyorlando.orlando.ui.extensions.showProgress
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private var searchCharacter = SearchCharacter()
    private val adapter = CharacterAdapter(clickOnCharacter = { clickOnCharacter(it) })
    private val viewModel: CharacterViewModel by viewModels()

    override fun setUpUi() = with(binding) {
        recyclerView.adapter = adapter
        swipRefresh.setOnRefreshListener {
            swipRefresh.isRefreshing = false
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
            searchCharacter.name = it
        },
        onMenuItemActionCollapse = {
            
        }
    )


    private fun getCharacters() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getCharactersPagingSource().collectLatest { characters ->
                adapter.submitData(characters)
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


    /*

    private fun searchCharacters() {
        viewModel.searchCharacters(
            page = page.toString(),
            searchCharacter = searchCharacter
        )
    }

    private fun resetSearch() {
        searchCharacter.name = ""
        searchCharacter.status = ""
        searchCharacter.species = ""
        searchCharacter.gender = ""
    }

    override fun observerViewModel() {
        super.observerViewModel()
        observeSearchCharacters()
    }


    private fun resetPaging() {
        characterSearchList.clear()
        page = 1
    }

    private fun observeSearchCharacters() {
        viewModel.searchCharacterResponse.observe(viewLifecycleOwner) { apiState ->
            apiState?.let {
                shouldShowProgress(apiState is ApiState.Loading)
                when (apiState) {
                    is ApiState.Success -> {
                        if (apiState.data != null) {
                            characterSearchList.addAll(apiState.data!!.results)
                            totalPages = apiState.data!!.info.pages
                            //adapter.setData(characterSearchList)
                            canCallToTheNextPage = true
                        }
                    }

                    is ApiState.Error -> {
                        if (apiState.codeError == 404) {
                            Log.w("ANDROID CHARACTER", "Character not found")
                        } else {
                            showErrorApi()
                        }
                    }

                    is ApiState.ErrorNetwork -> {
                        showErrorNetwork()
                    }

                    else -> {}
                }
            }
        }
    }
     */


    private fun clickOnCharacter(character: Character) {
        findNavController().navigate(
            CharacterFragmentDirections.actionCharacterFragmentToCharacterDetailFragment(
                character.id
            )
        )
    }


}