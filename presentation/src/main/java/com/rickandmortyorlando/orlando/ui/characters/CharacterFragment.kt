package com.rickandmortyorlando.orlando.ui.characters


import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import com.example.domain.models.remote.character.Character
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.databinding.FragmentCharacterBinding
import com.rickandmortyorlando.orlando.ui.base.BaseFragment
import com.rickandmortyorlando.orlando.ui.extensions.hideProgress
import com.rickandmortyorlando.orlando.ui.extensions.showErrorApi
import com.rickandmortyorlando.orlando.ui.extensions.showProgress
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CharacterFragment : BaseFragment<FragmentCharacterBinding>(R.layout.fragment_character) {

    private val viewModel: CharacterViewModel by viewModels()
    private val adapter = CharacterAdapter(clickOnCharacter = { clickOnCharacter(it) })

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true, toolbarTitle = getString(R.string.characters)
    )

    override fun configSearchView() = MainActivity.SearchViewConfig(showSearchView = true,
        showConfigIcon = true,
        clickOnSearchIcon = {
            findNavController().navigate(CharacterFragmentDirections.actionCharacterFragmentToSearchFragment())
        },
        clickOnConfigIcon = {
            findNavController().navigate(CharacterFragmentDirections.actionCharacterFragmentToSettingsFragment())
        })

    override fun setUpUi() = with(binding) {
        recyclerView.adapter = adapter
        swipRefresh.setOnRefreshListener {
            swipRefresh.isRefreshing = false
            viewModel.refreshCharactersPagingSource()
        }
        getCharacters()
        listenerAdapter()
    }

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


    private fun clickOnCharacter(character: Character) {
        findNavController().navigate(
            CharacterFragmentDirections.actionCharacterFragmentToCharacterDetailFragment(
                character.id
            )
        )
    }


}