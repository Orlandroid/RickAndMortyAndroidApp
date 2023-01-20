package com.example.paggingexample.ui.episode_detail

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.paggingexample.R
import com.example.paggingexample.data.models.remote.episode.Episode
import com.example.paggingexample.databinding.FragmentEpisodeDetailBinding
import com.example.paggingexample.ui.base.BaseFragment
import com.example.paggingexample.ui.characters.CharacterAdapter
import com.example.paggingexample.ui.characters.CharacterViewModel
import com.example.paggingexample.ui.extensions.*
import com.example.paggingexample.utils.removeCharactersForEpisodesList
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EpisodeDetailFragment :
    BaseFragment<FragmentEpisodeDetailBinding>(R.layout.fragment_episode_detail) {

    private val args: EpisodeDetailFragmentArgs by navArgs()
    private val viewModel: CharacterViewModel by viewModels()
    private val adapter = CharacterAdapter()
    override fun setUpUi() = with(binding) {
        toolbarLayout.toolbarBack.click {
            findNavController().popBackStack()
        }
        toolbarLayout.toolbarTitle.setText(R.string.episode_detail)
        recyclerCharacters.adapter = adapter
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
                viewModel.getManyCharacters(getListOfCharactersId(episode.characters))
            },
            {
                showErrorApi(
                    messageBody = it.message ?: "error",
                    shouldCloseTheViewOnApiError = true
                )
                shouldShowProgress(false)
            })
        queue.add(stringRequest)
    }

    override fun observerViewModel() {
        super.observerViewModel()
        observeApiResultGeneric(
            viewModel.manyCharactersResponse,
            hasProgressTheView = true,
            shouldCloseTheViewOnApiError = true
        ) {
            binding.tvCharacters.visible()
            adapter.setData(it)
        }
    }

    private fun getListOfCharactersId(episodesString: List<String>): String {
        val characters = arrayListOf<Int>()
        episodesString.forEach {
            characters.add(it.split("character/")[1].toInt())
        }
        return removeCharactersForEpisodesList(characters.toString())
    }

    private fun setDataDetail(episode: Episode) = with(binding) {
        tvNameEpisode.text = episode.name
        tvEpisodeNumber.text = episode.episode
        tvEpisodeDate.text = episode.air_date
    }

}