package com.rickandmortyorlando.orlando.features.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.databinding.FragmentEpisodesBinding
import com.rickandmortyorlando.orlando.features.base.BaseFragment
import com.rickandmortyorlando.orlando.features.extensions.content
import com.rickandmortyorlando.orlando.features.extensions.navigateAction
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EpisodesFragmentWrapper : BaseFragment<FragmentEpisodesBinding>(R.layout.fragment_episodes) {


    override fun setUpUi() {

    }

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true, toolbarTitle = getString(R.string.episodes)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return content {
            val viewModel: EpisodesViewModel = hiltViewModel()
            val episodes = viewModel.episodes.collectAsLazyPagingItems()
            EpisodesScreen(episodes = episodes, clickOnItem = ::clickOnEpisode)
        }
    }


    private fun clickOnEpisode(episodeId: Int) {
        navigateAction(EpisodesFragmentWrapperDirections.navigationToEpisodeDetailWrapper(episodeId))
    }

}