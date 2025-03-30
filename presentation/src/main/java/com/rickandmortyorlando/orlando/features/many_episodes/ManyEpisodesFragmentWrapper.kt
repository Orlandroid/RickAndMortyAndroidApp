package com.rickandmortyorlando.orlando.features.many_episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.components.ErrorScreen
import com.rickandmortyorlando.orlando.components.ItemEpisode
import com.rickandmortyorlando.orlando.components.skeletons.EpisodeSkeleton
import com.rickandmortyorlando.orlando.databinding.FragmentEpisodesBinding
import com.rickandmortyorlando.orlando.features.base.BaseFragment
import com.rickandmortyorlando.orlando.features.extensions.content
import com.rickandmortyorlando.orlando.features.extensions.setStatusBarColor
import com.rickandmortyorlando.orlando.state.BaseViewState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ManyEpisodesFragmentWrapper :
    BaseFragment<FragmentEpisodesBinding>(R.layout.fragment_episodes) {

    private val args: ManyEpisodesFragmentWrapperArgs by navArgs()

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = getString(R.string.episodes)
    )

    override fun setUpUi() {
        setStatusBarColor(R.color.status_bar_color)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return content {
            val viewModel: ManyEpisodesViewModel = hiltViewModel()
            LaunchedEffect(Unit) {
                viewModel.getEpisodes(args.idsEpisodes)
            }
            val state = viewModel.state.collectAsStateWithLifecycle()
            when (val currentState = state.value) {
                is BaseViewState.Loading -> {
                    Column {
                        for (i in 0..15) {
                            EpisodeSkeleton()
                        }
                    }
                }

                is BaseViewState.Content -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(currentState.result) { episode ->
                            ItemEpisode(
                                episode = episode,
                                clickOnItem = { episodesId ->
                                    findNavController().navigate(
                                        ManyEpisodesFragmentWrapperDirections.navigationToEpisodeDetailWrapper(
                                            episodesId
                                        )
                                    )
                                }
                            )
                        }
                    }
                }

                is BaseViewState.Error -> {
                    ErrorScreen()
                }
            }
        }
    }

}