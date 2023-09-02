package com.rickandmortyorlando.orlando.ui.episodes

import androidx.fragment.app.viewModels
import com.example.domain.models.remote.episode.Episode
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.databinding.FragmentEpisodesBinding
import com.rickandmortyorlando.orlando.ui.base.BaseFragment
import com.rickandmortyorlando.orlando.ui.extensions.gone
import com.rickandmortyorlando.orlando.ui.extensions.hideProgress
import com.rickandmortyorlando.orlando.ui.extensions.myOnScrolled
import com.rickandmortyorlando.orlando.ui.extensions.navigateAction
import com.rickandmortyorlando.orlando.ui.extensions.observeApiResultGeneric
import com.rickandmortyorlando.orlando.ui.extensions.showProgress
import com.rickandmortyorlando.orlando.ui.extensions.visible
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EpisodesFragment : BaseFragment<FragmentEpisodesBinding>(R.layout.fragment_episodes) {

    private val viewModel: EpisodesViewModel by viewModels()
    private var adapter = EpisodesAdapter { clickOnEpisode(it) }
    private var currentPage = 1
    private var totalPages = 0
    private var canCallToTheNextPage = true
    private var episodesList: ArrayList<Episode> = arrayListOf()
    private var isFirsTimeOneTheView = true

    override fun setUpUi() = with(binding) {
        if (isFirsTimeOneTheView) {
            resetPaging()
            viewModel.getEpisodes(currentPage)
            isFirsTimeOneTheView = false
        }
        recyclerEpisodes.adapter = adapter
        recyclerEpisodes.myOnScrolled {
            if (!canCallToTheNextPage) return@myOnScrolled
            if (totalPages > currentPage) {
                currentPage++
                canCallToTheNextPage = false
                viewModel.getEpisodes(page = currentPage)
            }
        }
    }

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true, toolbarTitle = getString(R.string.episodes)
    )

    override fun observerViewModel() {
        super.observerViewModel()
        observeApiResultGeneric(
            viewModel.episodeResponse,
            onLoading = {
                onLoading()
            },
            onFinishLoading = {
                onFinishLoading()
            }
        ) {
            episodesList.addAll(it.results)
            adapter.setData(episodesList)
            canCallToTheNextPage = true
            totalPages = it.info.pages
        }
    }

    private fun onLoading() {
        if (currentPage > 1) {
            showProgress()
            return
        }
        binding.skeleton.visible()
    }

    private fun onFinishLoading() {
        if (currentPage > 1) {
            hideProgress()
            return
        }
        binding.skeleton.gone()
    }

    private fun clickOnEpisode(idOfEpisode: Int) {
        val action =
            EpisodesFragmentDirections.actionEpisodesFragmentToEpisodeDetailFragment(idOfEpisode)
        navigateAction(action)
    }

    private fun resetPaging() {
        episodesList.clear()
        currentPage = 1
    }


}