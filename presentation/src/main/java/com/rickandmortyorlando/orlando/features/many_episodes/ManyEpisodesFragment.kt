package com.rickandmortyorlando.orlando.features.many_episodes

import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.PagingData
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.domain.models.episodes.Episode
import com.google.gson.Gson
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.databinding.FragmentEpisodesBinding
import com.rickandmortyorlando.orlando.features.base.BaseFragment
import com.rickandmortyorlando.orlando.features.episodes.EpisodesAdapter
import com.rickandmortyorlando.orlando.features.episodes.EpisodesViewModel
import com.rickandmortyorlando.orlando.features.extensions.observeApiResultGeneric
import com.rickandmortyorlando.orlando.features.extensions.setStatusBarColor
import com.rickandmortyorlando.orlando.features.extensions.shouldShowProgress
import com.rickandmortyorlando.orlando.features.extensions.showErrorApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ManyEpisodesFragment : BaseFragment<FragmentEpisodesBinding>(R.layout.fragment_episodes) {

    private val viewModel: EpisodesViewModel by viewModels()
    private var adapter: EpisodesAdapter? = null
    private val args: ManyEpisodesFragmentArgs by navArgs()

    override fun setUpUi() = with(binding) {
        setStatusBarColor(R.color.status_bar_color)
        (requireActivity() as MainActivity).changeToolbarColor(
            resources.getColor(R.color.status_bar_color).toDrawable()
        )
        adapter = EpisodesAdapter {
            val action =
                ManyEpisodesFragmentDirections.navigationToCharacterDetailWrapper(it.id)
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
            viewLifecycleOwner.lifecycleScope.launch {
                adapter?.submitData(PagingData.from(it))
            }
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
                viewLifecycleOwner.lifecycleScope.launch {
                    adapter?.submitData(PagingData.from(listOf(episode)))
                }
                shouldShowProgress(false)
            },
            {
                showErrorApi(messageBody = it.message ?: "error")
                shouldShowProgress(false)
            })
        queue.add(stringRequest)
    }

}