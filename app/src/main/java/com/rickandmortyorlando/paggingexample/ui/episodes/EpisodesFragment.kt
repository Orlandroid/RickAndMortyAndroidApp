package com.rickandmortyorlando.paggingexample.ui.episodes

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rickandmortyorlando.paggingexample.R
import com.rickandmortyorlando.paggingexample.data.models.remote.episode.Episode
import com.rickandmortyorlando.paggingexample.databinding.FragmentEpisodesBinding
import com.rickandmortyorlando.paggingexample.ui.base.BaseFragment
import com.rickandmortyorlando.paggingexample.ui.extensions.click
import com.rickandmortyorlando.paggingexample.ui.extensions.myOnScrolled
import com.rickandmortyorlando.paggingexample.ui.extensions.navigateAction
import com.rickandmortyorlando.paggingexample.ui.extensions.observeApiResultGeneric
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EpisodesFragment : BaseFragment<FragmentEpisodesBinding>(R.layout.fragment_episodes) {

    private val viewModel: EpisodesViewModel by viewModels()
    private var adapter: EpisodesAdapter? = null
    private var currentPage = 1
    private var totalPages = 0
    private var canCallToTheNextPage = true
    private var episodesList: ArrayList<Episode> = arrayListOf()
    private var isFirsTimeOneTheView = true

    override fun setUpUi() = with(binding) {
        resetPaging()
        viewModel.getEpisodes(currentPage)
        isFirsTimeOneTheView = false
        toolbarLayout.toolbarTitle.text = getString(R.string.episodes)
        toolbarLayout.toolbarBack.click {
            findNavController().popBackStack()
        }
        adapter = EpisodesAdapter {
            val action =
                EpisodesFragmentDirections.actionEpisodesFragmentToEpisodeDetailFragment(it)
            navigateAction(action)
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

    override fun observerViewModel() {
        super.observerViewModel()
        observeApiResultGeneric(viewModel.episodeResponse, hasProgressTheView = true) {
            episodesList.addAll(it.results)
            adapter?.setData(episodesList)
            canCallToTheNextPage = true
            totalPages = it.info.pages
        }
    }

    private fun resetPaging() {
        episodesList.clear()
        currentPage = 1
    }


}