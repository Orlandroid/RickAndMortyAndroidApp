package com.example.paggingexample.ui.episodes

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.paggingexample.R
import com.example.paggingexample.databinding.FragmentEpisodesBinding
import com.example.paggingexample.ui.base.BaseFragment
import com.example.paggingexample.ui.extensions.click
import com.example.paggingexample.ui.extensions.hideProgress
import com.example.paggingexample.ui.extensions.showErrorApi
import com.example.paggingexample.ui.extensions.showProgress
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class EpisodesFragment : BaseFragment<FragmentEpisodesBinding>(R.layout.fragment_episodes) {

    private val viewModel: EpisodesViewModel by viewModels()
    private var adapter: EpisodesAdapter? = null

    override fun setUpUi() = with(binding) {
        adapter = EpisodesAdapter()
        binding.recyclerEpisodes.adapter = adapter
        listenerAdapter()
        getEpisodes()
        toolbarLayout.toolbarTitle.text = getString(R.string.episodes)
        toolbarLayout.toolbarBack.click {
            findNavController().popBackStack()
        }
    }

    private fun getEpisodes() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getEpisodes().collectLatest { episodes ->
                adapter?.submitData(episodes)
            }
        }

    }

    private fun listenerAdapter() {
        adapter?.addLoadStateListener { loadState ->
            if (loadState.source.append is LoadState.Loading || loadState.source.refresh is LoadState.Loading) {
                showProgress()
            } else {
                hideProgress()
            }
            when (loadState.source.refresh) {
                is LoadState.Error -> {
                    showErrorApi(true)
                }
                else -> {}
            }
        }
    }


}