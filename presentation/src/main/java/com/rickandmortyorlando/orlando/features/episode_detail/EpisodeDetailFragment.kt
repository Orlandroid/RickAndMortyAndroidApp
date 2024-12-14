package com.rickandmortyorlando.orlando.features.episode_detail

import androidx.compose.ui.res.stringResource
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.domain.models.remote.character.Character
import com.example.domain.models.remote.episode.Episode
import com.google.gson.Gson
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.components.TextLink
import com.rickandmortyorlando.orlando.databinding.FragmentEpisodeDetailBinding
import com.rickandmortyorlando.orlando.features.base.BaseFragment
import com.rickandmortyorlando.orlando.features.characters.CharacterGridAdapter
import com.rickandmortyorlando.orlando.features.characters.CharacterViewModel
import com.rickandmortyorlando.orlando.features.extensions.changeToolbarTitle
import com.rickandmortyorlando.orlando.features.extensions.click
import com.rickandmortyorlando.orlando.features.extensions.observeApiResultGeneric
import com.rickandmortyorlando.orlando.features.extensions.openYoutubeApp
import com.rickandmortyorlando.orlando.features.extensions.shouldShowProgress
import com.rickandmortyorlando.orlando.features.extensions.showErrorApi
import com.rickandmortyorlando.orlando.features.extensions.visible
import com.rickandmortyorlando.orlando.utils.removeCharactersForEpisodesList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class EpisodeDetailFragment :
    BaseFragment<FragmentEpisodeDetailBinding>(R.layout.fragment_episode_detail) {

    private val args: EpisodeDetailFragmentArgs by navArgs()
    private val viewModel: CharacterViewModel by viewModels()
    private var adapter = CharacterGridAdapter(clickOnCharacter = { clickOnCharacter(it) })
    override fun setUpUi() = with(binding) {
        binding.tvWatch.setContent {
            TextLink(text = stringResource(R.string.watch))
        }
        recyclerCharacters.adapter = adapter
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        recyclerCharacters.layoutManager = gridLayoutManager
        getOnlineOneEpisode()
    }

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = getString(R.string.episode_detail),
        clickOnBack = { onBackButton() }
    )

    private fun onBackButton() {
        findNavController().popBackStack()
    }

    private fun getOnlineOneEpisode() {
        binding.skeleton.showSkeleton()
        val queue = Volley.newRequestQueue(requireContext())
        val url = "https://rickandmortyapi.com/api/episode/${args.idEpisode}"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val episode = Gson().fromJson(response, Episode::class.java)
                setDataDetail(episode)
                viewModel.getManyCharacters(getListOfCharactersId(episode.characters))
                binding.skeleton.showOriginal()
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
            lifecycleScope.launch {
                adapter.submitData(PagingData.from(it))
            }
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
        changeToolbarTitle(episode.name)
        tvWatch.click {
            requireContext().openYoutubeApp("${episode.name} ${episode.episode}")
        }
    }

    private fun clickOnCharacter(character: Character) {
        findNavController().navigate(
            EpisodeDetailFragmentDirections.navigationToCharacterDetail(
                character.id
            )
        )
    }

}