package com.rickandmortyorlando.orlando.features.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.fragment.findNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.components.ToolbarConfiguration
import com.rickandmortyorlando.orlando.features.base.BaseComposeScreen
import com.rickandmortyorlando.orlando.features.extensions.content
import com.rickandmortyorlando.orlando.features.extensions.navigateAction
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EpisodesFragmentWrapper : Fragment(R.layout.fragment_episodes) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return content {
            val viewModel: EpisodesViewModel = hiltViewModel()
            val episodes = viewModel.episodes.collectAsLazyPagingItems()
            BaseComposeScreen(
                toolbarConfiguration = ToolbarConfiguration(
                    title = stringResource(R.string.episodes),
                    clickOnBackButton = {
                        findNavController().navigateUp()
                    }
                )
            ) {
                EpisodesScreen(
                    episodes = episodes,
                    clickOnItem = ::clickOnEpisode,
                    clickOnBackButton = {})
            }
        }
    }


    private fun clickOnEpisode(episodeId: Int) {
        navigateAction(EpisodesFragmentWrapperDirections.navigationToEpisodeDetailWrapper(episodeId))
    }

}