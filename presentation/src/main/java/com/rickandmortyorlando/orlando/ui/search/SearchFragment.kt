package com.rickandmortyorlando.orlando.ui.search

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.domain.state.ApiState
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.example.domain.models.local.SearchCharacter
import com.example.domain.models.remote.character.Character
import com.rickandmortyorlando.orlando.databinding.FragmentSearchBinding
import com.rickandmortyorlando.orlando.ui.base.BaseFragment
import com.rickandmortyorlando.orlando.ui.characters.CharacterAdapter
import com.rickandmortyorlando.orlando.ui.characters.CharacterViewModel
import com.rickandmortyorlando.orlando.ui.extensions.myOnScrolled
import com.rickandmortyorlando.orlando.ui.extensions.shouldShowProgress
import com.rickandmortyorlando.orlando.ui.extensions.showErrorApi
import com.rickandmortyorlando.orlando.ui.extensions.showErrorNetwork
import com.rickandmortyorlando.orlando.ui.extensions.showProgress
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private var canCallToTheNextPage = true
    private var page = 1
    private var totalPages = 0
    var searchCharacter = SearchCharacter()
    private val adapter = CharacterAdapter()
    private var characterSearchList: ArrayList<Character> = arrayListOf()
    private val viewModel: CharacterViewModel by viewModels()
    private var isFirsTimeOneTheView = true

    override fun setUpUi() = with(binding) {
        recyclerView.adapter = adapter
        adapter.setListener(object : CharacterAdapter.ClickOnCharacter {
            override fun clickOnCharacter(character: Character) {
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToCharacterDetailFragment(
                        character.id
                    )
                )
            }
        })
        recyclerView.myOnScrolled {
            if (!canCallToTheNextPage) {
                return@myOnScrolled
            }
            if (totalPages > page) {
                page++
                canCallToTheNextPage = false
                searchCharacters()
                showProgress()
            }
        }
        swipRefresh.setOnRefreshListener {
            adapter.setData(listOf())
            resetPaging()
            resetSearch()
            swipRefresh.isRefreshing = false
            searchCharacters()
        }
        if (isFirsTimeOneTheView) {
            searchCharacters()
            isFirsTimeOneTheView = false
        }
    }

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = getString(R.string.search)
    )

    override fun configSearchView() = MainActivity.SearchViewConfig(
        showSearchView = true,
        onQueryTextSubmit = {
            characterSearchList.clear()
            page = 1
            searchCharacter.name = it
            searchCharacters()
        },
        onMenuItemActionCollapse = {
            page = 1
            resetSearch()
            resetPaging()
            searchCharacters()
        }
    )


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
                            adapter.setData(characterSearchList)
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


}