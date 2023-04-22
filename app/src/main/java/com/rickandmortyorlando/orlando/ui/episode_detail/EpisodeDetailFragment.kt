package com.rickandmortyorlando.orlando.ui.episode_detail

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.data.models.remote.character.Character
import com.rickandmortyorlando.orlando.data.models.remote.episode.Episode
import com.rickandmortyorlando.orlando.databinding.FragmentEpisodeDetailBinding
import com.rickandmortyorlando.orlando.ui.base.BaseFragment
import com.rickandmortyorlando.orlando.ui.characters.CharacterAdapter
import com.rickandmortyorlando.orlando.ui.characters.CharacterViewModel
import com.rickandmortyorlando.orlando.ui.extensions.*
import com.rickandmortyorlando.orlando.utils.removeCharactersForEpisodesList
import com.google.gson.Gson
import com.rickandmortyorlando.orlando.ui.episodes.EpisodesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EpisodeDetailFragment :
    BaseFragment<FragmentEpisodeDetailBinding>(R.layout.fragment_episode_detail) {

    private val args: EpisodeDetailFragmentArgs by navArgs()
    private val viewModel: CharacterViewModel by viewModels()
    private val episodesViewModel: EpisodesViewModel by navGraphViewModels(R.id.main_graph) {
        defaultViewModelProviderFactory
    }
    private var adapter = CharacterAdapter()
    override fun setUpUi() = with(binding) {
        toolbarLayout.toolbarBack.click {
            if (episodesViewModel.comesFromEpisodesMainMenu) {
                findNavController().popBackStack()
            } else {
                findNavController().popBackStack(R.id.characterFragment, false)
            }
        }
        toolbarLayout.toolbarTitle.setText(R.string.episode_detail)
        recyclerCharacters.adapter = adapter
        adapter.setListener(object : CharacterAdapter.ClickOnCharacter {
            override fun clickOnCharacter(character: Character) {
                val action =
                    EpisodeDetailFragmentDirections.actionEpisodeDetailFragmentToCharacterDetailFragment(
                        character.id
                    )
                navigateAction(action)
            }
        })
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
        toolbarLayout.toolbarTitle.text = episode.name
    }

}