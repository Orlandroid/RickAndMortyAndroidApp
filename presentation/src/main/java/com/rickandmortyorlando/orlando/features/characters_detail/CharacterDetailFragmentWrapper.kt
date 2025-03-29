package com.rickandmortyorlando.orlando.features.characters_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.components.skeletons.CharacterDetailSkeleton
import com.rickandmortyorlando.orlando.components.skeletons.EpisodeDetailSkeleton
import com.rickandmortyorlando.orlando.databinding.FragmentCharacterDetailBinding
import com.rickandmortyorlando.orlando.features.base.BaseFragment
import com.rickandmortyorlando.orlando.features.extensions.changeToolbarTitle
import com.rickandmortyorlando.orlando.features.extensions.content
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CharacterDetailFragmentFragmentWrapper :
    BaseFragment<FragmentCharacterDetailBinding>(R.layout.fragment_character_detail) {

    private val args: CharacterDetailFragmentFragmentWrapperArgs by navArgs()

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(showToolbar = true)

    override fun setUpUi() {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return content {
            val viewModel: CharacterDetailViewModel = hiltViewModel()
            LaunchedEffect(viewModel) {
                viewModel.getCharacterDetailInfo(args.charcaterId.toString())
            }
            val characterDetailStata = viewModel.state.collectAsStateWithLifecycle()
            when (characterDetailStata.value) {
                CharacterDetailState.Loading -> {
                    CharacterDetailSkeleton()
                }

                is CharacterDetailState.CharacterDetailUiState -> {
                    val state =
                        characterDetailStata.value as CharacterDetailState.CharacterDetailUiState
                    changeToolbarTitle(state.characterDetail.name)
                    CharacterDetailScreen(
                        uiState = state,
                        clickOnCharacter = {
                            //Todo add validation to only navigate to detail screen when the character that we click is different of the current one
                            findNavController().navigate(CharacterDetailFragmentFragmentWrapperDirections.navigationToCharacterDetailWrapper(it))
                        },
                        clickOnNumberOfEpisodes = {
                            val idsEpisodes = getListOfEpisodes(state.characterDetail.episode)
                            findNavController().navigate(
                                CharacterDetailFragmentFragmentWrapperDirections.actionCharacterDetailFragmentWrapperToManyEpisodesFragmentWrapper(
                                    idsEpisodes = idsEpisodes
                                )
                            )
                        }
                    )
                }

                is CharacterDetailState.Error -> {
                    //Todo add error screen
                }
            }
        }
    }

}