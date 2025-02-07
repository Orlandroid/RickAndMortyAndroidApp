package com.rickandmortyorlando.orlando.features.episodes

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.example.domain.models.episodes.Episode
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.databinding.FragmentEpisodesBinding
import com.rickandmortyorlando.orlando.features.base.BaseFragment
import com.rickandmortyorlando.orlando.features.extensions.getError
import com.rickandmortyorlando.orlando.features.extensions.hideProgress
import com.rickandmortyorlando.orlando.features.extensions.navigateAction
import com.rickandmortyorlando.orlando.features.extensions.showError
import com.rickandmortyorlando.orlando.features.extensions.showErrorApi
import com.rickandmortyorlando.orlando.features.extensions.showProgress
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class EpisodesFragment : BaseFragment<FragmentEpisodesBinding>(R.layout.fragment_episodes) {

    private val viewModel: EpisodesViewModel by viewModels()
    private var adapter = EpisodesAdapter { clickOnEpisode(it) }

    override fun setUpUi() = with(binding) {
        showProgress()
        recyclerEpisodes.adapter = adapter
        getEpisodes()
        listenerAdapter()
    }

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true, toolbarTitle = getString(R.string.episodes)
    )

    private fun getEpisodes() = viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.getEpisodesPagingSource.collectLatest { episodes ->
                adapter.submitData(episodes)
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

    private fun clickOnEpisode(item: Episode) {
        val action =
            EpisodesFragmentDirections.navigationToEpisodeDetail(item.id)
        navigateAction(action)
    }

}