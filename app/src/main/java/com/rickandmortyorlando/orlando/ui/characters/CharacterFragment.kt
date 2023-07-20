package com.rickandmortyorlando.orlando.ui.characters


import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.domain.state.ApiState
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.example.domain.models.remote.character.Character
import com.rickandmortyorlando.orlando.databinding.FragmentCharacterBinding
import com.rickandmortyorlando.orlando.ui.base.BaseFragment
import com.rickandmortyorlando.orlando.ui.extensions.*
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
        if (isFirsTimeOneTheView) {
            resetPaging()
            viewModel.getCharacters(page.toString())
            isFirsTimeOneTheView = false
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


    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = getString(R.string.characters)
    )

    override fun configSearchView() = MainActivity.SearchViewConfig(
        showSearchView = true,
        showConfigIcon = true,
        clickOnSearchIcon = {
            findNavController().navigate(CharacterFragmentDirections.actionCharacterFragmentToSearchFragment())
        },
        clickOnConfigIcon = {
            findNavController().navigate(CharacterFragmentDirections.actionCharacterFragmentToSettingsFragment())
        }
    )


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
                            characterList.addAll(apiState.data!!.results)
                            totalPages = apiState.data!!.info.pages
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


}