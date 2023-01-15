package com.example.paggingexample.ui.episodes

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.paggingexample.R
import com.example.paggingexample.databinding.FragmentEpisodesBinding
import com.example.paggingexample.ui.base.BaseFragment
import com.example.paggingexample.ui.extensions.click
import com.example.paggingexample.ui.extensions.observeApiResultGeneric
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ManyEpisodesFragment : BaseFragment<FragmentEpisodesBinding>(R.layout.fragment_episodes) {

    private val viewModel: EpisodesViewModel by viewModels()
    private var adapter: EpisodesAdapter? = null
    private val args: ManyEpisodesFragmentArgs by navArgs()

    override fun setUpUi() = with(binding) {
        toolbarLayout.toolbarTitle.text = getString(R.string.episodes)
        toolbarLayout.toolbarBack.click {
            findNavController().popBackStack()
        }
        adapter = EpisodesAdapter()
        recyclerEpisodes.adapter = adapter
        viewModel.getManyEpisodesResponse(args.idsEpisodes)
    }

    override fun observerViewModel() {
        super.observerViewModel()
        observeApiResultGeneric(viewModel.manyEpisodesResponse, hasProgressTheView = true) {
            adapter?.setData(it)
        }
    }

}