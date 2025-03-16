package com.rickandmortyorlando.orlando.features.episode_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.navArgs
import com.example.data.model.character.toCharacter
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.databinding.FragmentEpisodeDetailBinding
import com.rickandmortyorlando.orlando.features.base.BaseFragment
import com.rickandmortyorlando.orlando.features.extensions.changeToolbarTitle
import com.rickandmortyorlando.orlando.features.extensions.content

class EpisodeDetailFragmentWrapper :
    BaseFragment<FragmentEpisodeDetailBinding>(R.layout.fragment_episode_detail) {

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        toolbarTitle = getString(R.string.episode_detail),
        showToolbar = true
    )


    private val args: EpisodeDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return content {
            val viewModel: EpisodeDetailViewModel = hiltViewModel()
            LaunchedEffect(Unit) {
                viewModel.getEpisodeInfo(args.idEpisode.toString())
            }
            val state = viewModel.state.collectAsStateWithLifecycle()
            when (state.value) {
                is EpisodeDetailViewState.Error -> {
                    Text((state.value as EpisodeDetailViewState.Error).message)
                }

                is EpisodeDetailViewState.Loading -> {

                }

                is EpisodeDetailViewState.Success -> {
                    val response = (state.value as EpisodeDetailViewState.Success)
                    EpisodeDetailScreen(
                        episode = response.episode,
                        characters = response.characters.map { it.toCharacter() }
                    )
                    changeToolbarTitle(response.episode.name)
                }
            }
        }
    }


    override fun setUpUi() {

    }

}