package com.rickandmortyorlando.orlando.features.characters


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.fragment.findNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.components.ToolbarConfiguration
import com.rickandmortyorlando.orlando.features.base.BaseComposeScreen
import com.rickandmortyorlando.orlando.features.extensions.content
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CharacterFragmentWrapper : Fragment(R.layout.fragment_character) {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return content {
            val viewModel: CharacterViewModel = hiltViewModel()
            val characters = viewModel.characters.collectAsLazyPagingItems()
            BaseComposeScreen(
                toolbarConfiguration = ToolbarConfiguration(
                    title = stringResource(R.string.characters),
                    clickOnBackButton = { findNavController().navigateUp() },
                    actions = {
                        IconButton(
                            onClick = {
                                findNavController().navigate(CharacterFragmentWrapperDirections.actionCharacterFragmentWrapperToSearchCharactersFragmentWrapper())
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Search",
                                tint = Color.White
                            )
                        }
                    }
                )
            ) {
                CharactersScreen(
                    characters = characters,
                    clickOnItem = ::clickOnCharacter
                )
            }
        }
    }


    private fun clickOnCharacter(characterId: Int, name: String) {
        findNavController().navigate(
            CharacterFragmentWrapperDirections.navigationToCharacterDetailWrapper(
                characterId,
                name
            )
        )
    }


}