package com.example.paggingexample.ui.episode_detail

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.paggingexample.R
import com.example.paggingexample.data.models.remote.episode.Episode
import com.example.paggingexample.databinding.FragmentEpisodeDetailBinding
import com.example.paggingexample.ui.base.BaseFragment
import com.example.paggingexample.ui.extensions.click
import com.example.paggingexample.ui.extensions.shouldShowProgress
import com.example.paggingexample.ui.extensions.showErrorApi
import com.google.gson.Gson


class EpisodeDetailFragment :
    BaseFragment<FragmentEpisodeDetailBinding>(R.layout.fragment_episode_detail) {

    private val args: EpisodeDetailFragmentArgs by navArgs()
    override fun setUpUi() = with(binding) {
        toolbarLayout.toolbarBack.click {
            findNavController().popBackStack()
        }
        toolbarLayout.toolbarTitle.setText(R.string.episode_detail)
        getOnlineOneEpisode()
    }

    private fun getOnlineOneEpisode() {
        binding.skeleton.showSkeleton()
        val queue = Volley.newRequestQueue(requireContext())
        val url = "https://rickandmortyapi.com/api/episode/${args.idEpisode}"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val episode = Gson().fromJson(response, Episode::class.java)
                binding.skeleton.showOriginal()
                setDataDetail(episode)
            },
            {
                showErrorApi(messageBody = it.message ?: "error")
                shouldShowProgress(false)
            })
        queue.add(stringRequest)
    }

    private fun setDataDetail(episode: Episode) = with(binding) {
        tvNameEpisode.text = episode.name
        tvEpisodeNumber.text = episode.episode
        tvEpisodeDate.text = episode.air_date
    }

}