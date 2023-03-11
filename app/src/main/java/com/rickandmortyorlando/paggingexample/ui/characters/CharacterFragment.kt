package com.rickandmortyorlando.paggingexample.ui.characters


import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rickandmortyorlando.paggingexample.R
import com.rickandmortyorlando.paggingexample.data.models.remote.character.Character
import com.rickandmortyorlando.paggingexample.data.state.ApiState
import com.rickandmortyorlando.paggingexample.databinding.FragmentCharacterBinding
import com.rickandmortyorlando.paggingexample.ui.base.BaseFragment
import com.rickandmortyorlando.paggingexample.ui.extensions.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CharacterFragment : BaseFragment<FragmentCharacterBinding>(R.layout.fragment_character) {

    private val viewModel: CharacterViewModel by viewModels()
    private var page = 1
    private var totalPages = 0
    private val adapter = CharacterAdapter()
    private var canCallToTheNextPage = true
    private var characterList: ArrayList<Character> = arrayListOf()
    private var isFirsTimeOneTheView = true


    override fun setUpUi() = with(binding) {
        enableToolbarForListeners(binding.toolbarLayout.toolbar)
        if (isFirsTimeOneTheView) {
            resetPaging()
            viewModel.getCharacters(page.toString())
            isFirsTimeOneTheView = false
        }
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
                viewModel.getCharacters(page = page.toString())
                showProgress()
            }
        }
        swipRefresh.setOnRefreshListener {
            adapter.setData(listOf())
            resetPaging()
            swipRefresh.isRefreshing = false
            viewModel.getCharacters(page.toString())
        }
    }

    private fun resetPaging() {
        characterList.clear()
        page = 1
    }

    override fun observerViewModel() {
        super.observerViewModel()
        observeCharactersResponse()
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        activity?.menuInflater?.inflate(R.menu.menu_search_witout, menu)
        val searchItem = menu.findItem(R.id.search)
        val settings = menu.findItem(R.id.settings)
        settings.setOnMenuItemClickListener {
            findNavController().navigate(CharacterFragmentDirections.actionCharacterFragmentToSettingsFragment())
            false
        }
        searchItem.setOnMenuItemClickListener {
            findNavController().navigate(CharacterFragmentDirections.actionCharacterFragmentToSearchFragment())
            false
        }
    }

}