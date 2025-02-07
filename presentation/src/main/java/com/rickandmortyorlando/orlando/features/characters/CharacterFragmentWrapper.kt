package com.rickandmortyorlando.orlando.features.characters


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.fragment.findNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.domain.models.characters.Character
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.databinding.FragmentCharacterBinding
import com.rickandmortyorlando.orlando.features.base.BaseFragment
import com.rickandmortyorlando.orlando.features.extensions.content
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CharacterFragmentWrapper :
    BaseFragment<FragmentCharacterBinding>(R.layout.fragment_character) {

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true, toolbarTitle = getString(R.string.characters)
    )

    override fun configSearchView() = MainActivity.SearchViewConfig(
        showSearchView = true,
        clickOnSearchIcon = {
            findNavController().navigate(CharacterFragmentDirections.actionCharacterFragmentToSearchFragment())
        }
    )

    override fun setUpUi() {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return content {
            val viewModel: CharacterViewModel = hiltViewModel()
            val characters = viewModel.getCharactersPagingSource.collectAsLazyPagingItems()
            CharactersScreen(characters = characters, clickOnItem = ::clickOnCharacter)
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