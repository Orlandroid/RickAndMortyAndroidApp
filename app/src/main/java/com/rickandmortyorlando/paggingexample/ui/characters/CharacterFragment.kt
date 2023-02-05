package com.rickandmortyorlando.paggingexample.ui.characters

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rickandmortyorlando.paggingexample.R
import com.rickandmortyorlando.paggingexample.data.models.local.SearchCharacter
import com.rickandmortyorlando.paggingexample.data.models.remote.character.Character
import com.rickandmortyorlando.paggingexample.data.state.ApiState
import com.rickandmortyorlando.paggingexample.databinding.FragmentCharacterBinding
import com.rickandmortyorlando.paggingexample.ui.extensions.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CharacterFragment : Fragment() {

    private var _binding: FragmentCharacterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CharacterViewModel by viewModels()
    private var page = 1
    private var totalPages = 0
    private val adapter = CharacterAdapter()
    private var canCallToTheNextPage = true
    private var characterList: ArrayList<Character> = arrayListOf()
    private var characterSearchList: ArrayList<Character> = arrayListOf()
    private var isSearching = false
    var searchCharacter = SearchCharacter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterBinding.inflate(layoutInflater, container, false)
        setUpUi()
        setUpObserves()
        return binding.root
    }


    private fun setUpUi() = with(binding) {
        enableToolbarForListeners(binding.toolbarLayout.toolbar)
        isSearching = false
        resetPaging()
        viewModel.getCharacters(page.toString())
        toolbarLayout.toolbarTitle.text = getString(R.string.characters)
        toolbarLayout.toolbarBack.click {
            findNavController().popBackStack()
        }
        recyclerView.adapter = adapter
        adapter.setListener(object : CharacterAdapter.ClickOnCharacter {
            override fun clickOnCharacter(character: Character) {
                val action =
                    CharacterFragmentDirections.actionCharacterFragmentToCharacterDetailFragment(
                        character.id
                    )
                findNavController().navigate(action)
            }
        })
        recyclerView.myOnScrolled {
            if (!canCallToTheNextPage) {
                return@myOnScrolled
            }
            if (totalPages > page) {
                page++
                canCallToTheNextPage = false
                if (isSearching) {
                    viewModel.searchCharacters(page = page.toString(), searchCharacter = searchCharacter)
                } else {
                    viewModel.getCharacters(page = page.toString())
                }
                showProgress()
            }
        }
        swipRefresh.setOnRefreshListener {
            resetPaging()
            swipRefresh.isRefreshing = false
            isSearching = false
            viewModel.getCharacters(page.toString())
        }
    }

    private fun resetPaging() {
        characterSearchList.clear()
        characterList.clear()
        page = 1
    }


    private fun setUpObserves() {
        observeCharactersResponse()
        observeSearchCharacters()
    }

    private fun observeCharactersResponse() {
        viewModel.myCharacterResponse.observe(viewLifecycleOwner) { apiState ->
            apiState?.let {
                if (page > 1) {
                    shouldShowProgress(apiState is ApiState.Loading)
                } else {
                    binding.skeleton.shouldShowSkeleton(apiState is ApiState.Loading)
                }
                when (apiState) {
                    is ApiState.Success -> {
                        if (apiState.data != null) {
                            characterList.addAll(apiState.data.results)
                            totalPages = apiState.data.info.pages
                            adapter.setData(characterList)
                            canCallToTheNextPage = true
                            binding.root.setBackgroundColor(resources.getColor(R.color.background))
                        }
                    }
                    is ApiState.Error -> {
                        showErrorApi()
                    }
                    is ApiState.ErrorNetwork -> {
                        showErrorNetwork()
                    }
                    else -> {}
                }
            }
        }
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

    //Todo check Deorecation
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        myOnCreateOptionsMenu(
            menu = menu,
            myOnQueryTextSubmit = {
                characterSearchList.clear()
                page = 1
                searchCharacter.name = it
                viewModel.searchCharacters(searchCharacter = searchCharacter, page = page.toString())
            },
            myOnMenuItemActionCollapse = {
                isSearching = false
                page = 1
                viewModel.getCharacters(page.toString())
            },
            setonMenuItemActionExpand = {
                isSearching = true
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}