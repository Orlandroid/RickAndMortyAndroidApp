package com.rickandmortyorlando.orlando.features.characters


import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.domain.models.characters.Character
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.databinding.FragmentCharacterBinding
import com.rickandmortyorlando.orlando.features.base.BaseFragment
import com.rickandmortyorlando.orlando.features.extensions.getError
import com.rickandmortyorlando.orlando.features.extensions.hideProgress
import com.rickandmortyorlando.orlando.features.extensions.showError
import com.rickandmortyorlando.orlando.features.extensions.showErrorApi
import com.rickandmortyorlando.orlando.features.extensions.showProgress
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

    override fun configSearchView() = MainActivity.SearchViewConfig(
        showSearchView = true,
        clickOnSearchIcon = {
            findNavController().navigate(CharacterFragmentDirections.actionCharacterFragmentToSearchFragment())
        }
    )

    override fun setUpUi() = with(binding) {
        showProgress()
        recyclerView.adapter = adapter
        swipRefresh.setOnRefreshListener {
            swipRefresh.isRefreshing = false
            viewModel.refreshCharactersPagingSource()
        }
        getCharacters()
        listenerAdapter()
    }

    private fun getCharacters() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getCharactersPagingSource.collectLatest { characters ->
                    adapter.submitData(lifecycle, characters)
                }
            }
        }
    }

    private fun listenerAdapter() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.addLoadStateListener { loadState ->
                    if (loadState.source.append is LoadState.Loading || loadState.source.refresh is LoadState.Loading) {
                        showProgress()
                    } else {
                        hideProgress()
                    }
                    val errorState = loadState.getError()
                    errorState?.showError {
                        showErrorApi()
                    }
                }
            }
        }
    }


    private fun clickOnCharacter(character: Character) {
        findNavController().navigate(
            CharacterFragmentDirections.navigationToCharacterDetail(
                character.id
            )
        )
    }


}