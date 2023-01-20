package com.example.paggingexample.ui.episodes

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.paggingexample.R
import com.example.paggingexample.data.models.remote.episode.Episode
import com.example.paggingexample.databinding.FragmentEpisodesBinding
import com.example.paggingexample.ui.base.BaseFragment
import com.example.paggingexample.ui.extensions.click
import com.example.paggingexample.ui.extensions.observeApiResultGeneric
import com.example.paggingexample.ui.extensions.shouldShowProgress
import com.example.paggingexample.ui.extensions.showErrorApi
import com.google.gson.Gson
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
        adapter = EpisodesAdapter {
            val action =
                ManyEpisodesFragmentDirections.actionManyEpisodesFragmentToEpisodeDetailFragment(it)
            findNavController().navigate(action)
        }
        recyclerEpisodes.adapter = adapter
        if (args.isSingleEpisode) {
            getOnlineOneEpisode()
        } else {
            viewModel.getManyEpisodesResponse(args.idsEpisodes)
        }
    }

    override fun observerViewModel() {
        super.observerViewModel()
        observeApiResultGeneric(viewModel.manyEpisodesResponse, hasProgressTheView = true) {
            adapter?.setData(it)
        }
    }

    private fun getOnlineOneEpisode() {
        shouldShowProgress(true)
        val queue = Volley.newRequestQueue(requireContext())
        val url = "https://rickandmortyapi.com/api/episode/${args.idsEpisodes}"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val episode = Gson().fromJson(response, Episode::class.java)
                adapter?.setData(listOf(episode))
                shouldShowProgress(false)
            },
            {
                showErrorApi(messageBody = it.message ?: "error")
                shouldShowProgress(false)
            })
        queue.add(stringRequest)
    }

}