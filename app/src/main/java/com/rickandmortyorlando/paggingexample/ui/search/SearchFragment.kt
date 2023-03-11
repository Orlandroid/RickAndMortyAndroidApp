package com.rickandmortyorlando.paggingexample.ui.search

import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rickandmortyorlando.paggingexample.R
import com.rickandmortyorlando.paggingexample.data.models.local.SearchCharacter
import com.rickandmortyorlando.paggingexample.data.models.remote.character.Character
import com.rickandmortyorlando.paggingexample.data.state.ApiState
import com.rickandmortyorlando.paggingexample.databinding.FragmentSearchBinding
import com.rickandmortyorlando.paggingexample.ui.base.BaseFragment
import com.rickandmortyorlando.paggingexample.ui.characters.CharacterAdapter
import com.rickandmortyorlando.paggingexample.ui.characters.CharacterFragmentDirections
import com.rickandmortyorlando.paggingexample.ui.characters.CharacterViewModel
import com.rickandmortyorlando.paggingexample.ui.extensions.*
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

    override fun setUpUi() = with(binding) {
        enableToolbarForListeners(binding.toolbarLayout.toolbar)
        toolbarLayout.toolbarBack.click {
            findNavController().popBackStack()
        }
        toolbarLayout.toolbarTitle.text = getString(R.string.search)
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
            swipRefresh.isRefreshing = false
            searchCharacters()
        }
        searchCharacters()
    }

    private fun searchCharacters() {
        viewModel.searchCharacters(
            page = page.toString(),
            searchCharacter = searchCharacter
        )
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
                            characterSearchList.addAll(apiState.data.results)
                            totalPages = apiState.data.info.pages
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


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        myOnCreateOptionsMenu(
            menu = menu,
            myOnQueryTextSubmit = {
                characterSearchList.clear()
                page = 1
                searchCharacter.name = it
                searchCharacters()
            },
            myOnMenuItemActionCollapse = {
                page = 1
                resetSearch()
                resetPaging()
                searchCharacters()
            }
        )
    }

    private fun resetSearch() {
        searchCharacter.name = ""
        searchCharacter.status = ""
        searchCharacter.species = ""
        searchCharacter.gender = ""
    }

}