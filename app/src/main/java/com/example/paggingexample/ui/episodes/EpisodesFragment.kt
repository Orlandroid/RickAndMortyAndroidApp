package com.example.paggingexample.ui.episodes

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.paggingexample.R
import com.example.paggingexample.data.models.remote.location.episode.Episode
import com.example.paggingexample.databinding.FragmentEpisodesBinding
import com.example.paggingexample.ui.base.BaseFragment
import com.example.paggingexample.ui.extensions.click
import com.example.paggingexample.ui.extensions.myOnScrolled
import com.example.paggingexample.ui.extensions.observeApiResultGeneric
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EpisodesFragment : BaseFragment<FragmentEpisodesBinding>(R.layout.fragment_episodes) {

    private val viewModel: EpisodesViewModel by viewModels()
    private var adapter: EpisodesAdapter? = null
    private var currentPage = 1
    private var totalPages = 0
    private var canCallToTheNextPage = true
    private var episodesList: ArrayList<Episode> = arrayListOf()

    override fun setUpUi() = with(binding) {
        resetPaging()
        toolbarLayout.toolbarTitle.text = getString(R.string.episodes)
        toolbarLayout.toolbarBack.click {
            findNavController().popBackStack()
        }
        viewModel.getEpisodes(currentPage)
        adapter = EpisodesAdapter()
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