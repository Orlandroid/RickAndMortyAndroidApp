package com.rickandmortyorlando.orlando.features.characters_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.LaunchedEffect
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.components.ErrorScreen
import com.rickandmortyorlando.orlando.components.ToolbarConfiguration
import com.rickandmortyorlando.orlando.components.skeletons.CharacterDetailSkeleton
import com.rickandmortyorlando.orlando.features.base.BaseComposeScreen
import com.rickandmortyorlando.orlando.features.extensions.content
import com.rickandmortyorlando.orlando.state.BaseViewState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CharacterDetailFragmentFragmentWrapper : Fragment(R.layout.fragment_character_detail) {

    private val args: CharacterDetailFragmentFragmentWrapperArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return content {
            val viewModel: CharacterDetailViewModel = hiltViewModel()
            LaunchedEffect(Unit) {
                viewModel.getCharacterDetailInfo(args.charcaterId)
            }
            val state = viewModel.state.collectAsStateWithLifecycle()
            BaseComposeScreen(
                toolbarConfiguration = ToolbarConfiguration(
                    title = args.characterName,
                    clickOnBackButton = { findNavController().navigateUp() }
                )
            ) {
                when (val currentState = state.value) {
                    is BaseViewState.Loading -> {
                        CharacterDetailSkeleton()
                    }

                    is BaseViewState.Content -> {
                        CharacterDetailScreen(
                            uiState = currentState.result,
                            clickOnCharacter = { characterId, name ->
                                if (characterId != currentState.result.characterDetail.id) {
                                    findNavController().navigate(
                                        CharacterDetailFragmentFragmentWrapperDirections.navigationToCharacterDetailWrapper(
                                            characterId,
                                            name
                                        )
                                    )
                                }
                            },
                            clickOnNumberOfEpisodes = {
                                val idsEpisodes =
                                    getListOfEpisodes(currentState.result.characterDetail.episode)
                                findNavController().navigate(
                                    CharacterDetailFragmentFragmentWrapperDirections.actionCharacterDetailFragmentWrapperToManyEpisodesFragmentWrapper(
                                        idsEpisodes = idsEpisodes
                                    )
                                )
                            }
                        )
                    }

                    is BaseViewState.Error -> {
                        ErrorScreen()
                    }
                }
            }
        }
    }

}