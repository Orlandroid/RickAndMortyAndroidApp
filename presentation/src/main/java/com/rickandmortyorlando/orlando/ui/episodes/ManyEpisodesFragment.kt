package com.rickandmortyorlando.orlando.ui.episodes

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.example.domain.models.remote.episode.Episode
import com.rickandmortyorlando.orlando.databinding.FragmentEpisodesBinding
import com.rickandmortyorlando.orlando.ui.base.BaseFragment
import com.rickandmortyorlando.orlando.ui.extensions.observeApiResultGeneric
import com.rickandmortyorlando.orlando.ui.extensions.shouldShowProgress
import com.rickandmortyorlando.orlando.ui.extensions.showErrorApi
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ManyEpisodesFragment : BaseFragment<FragmentEpisodesBinding>(R.layout.fragment_episodes) {

    private val viewModel: EpisodesViewModel by viewModels()
    private var adapter: EpisodesAdapter? = null
    private val args: ManyEpisodesFragmentArgs by navArgs()

    override fun setUpUi() = with(binding) {
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

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = getString(R.string.episodes)
    )

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