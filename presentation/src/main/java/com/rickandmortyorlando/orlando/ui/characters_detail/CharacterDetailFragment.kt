package com.rickandmortyorlando.orlando.ui.characters_detail

import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.domain.models.remote.character.Character
import com.example.domain.models.remote.location.SingleLocation
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.databinding.FragmentCharacterDetailBinding
import com.rickandmortyorlando.orlando.ui.base.BaseFragment
import com.rickandmortyorlando.orlando.ui.extensions.click
import com.rickandmortyorlando.orlando.ui.extensions.observeApiResultGeneric
import com.rickandmortyorlando.orlando.ui.extensions.setMargins
import com.rickandmortyorlando.orlando.ui.extensions.setStatusBarColor
import com.rickandmortyorlando.orlando.utils.getColorStatus
import com.rickandmortyorlando.orlando.utils.getColorStatusResource
import com.rickandmortyorlando.orlando.utils.getNumberFromUrWithPrefix
import com.rickandmortyorlando.orlando.utils.loadCircularImage
import com.rickandmortyorlando.orlando.utils.removeCharactersForEpisodesList
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CharacterDetailFragment :
    BaseFragment<FragmentCharacterDetailBinding>(R.layout.fragment_character_detail) {

    private val viewModel: CharacterDetailViewModel by viewModels()
    private val args: CharacterDetailFragmentArgs by navArgs()
    private var idsOfEpisodesOfTheCharacter = ""


    override fun setUpUi() = with(binding) {
        skeletonInfo.showSkeleton()
        skeletonLocation.showSkeleton()
        viewModel.getCharacter(args.charcaterId.toString())
        binding.tvEpisodes.click {
            val isSingleEpisode = idsOfEpisodesOfTheCharacter.contains(",")
            val action =
                CharacterDetailFragmentDirections.actionCharacterDetailFragmentToManyEpisodesFragment(
                    idsOfEpisodesOfTheCharacter, !isSingleEpisode
                )
            findNavController().navigate(action)
        }
    }

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(showToolbar = true)

    override fun observerViewModel() {
        super.observerViewModel()
        observeApiResultGeneric(
            viewModel.characterResponse, shouldCloseTheViewOnApiError = true
        ) { character ->
            with(binding) {
                skeletonInfo.showOriginal()
                skeletonInfo.setBackgroundColor(resources.getColor(R.color.background))
                skeletonInfo.setMargins(0, 0, 0, 0)
                setDataToView(character)
                idsOfEpisodesOfTheCharacter = getListOfEpisodes(character.episode)
            }
            viewModel.getSingleLocation(
                getNumberFromUrWithPrefix(
                    character.location.url,
                    "location"
                )
            )
        }
        observeApiResultGeneric(
            viewModel.locationResponse, shouldCloseTheViewOnApiError = true
        ) { singleLocation ->
            setLocation(singleLocation)
        }
    }

    private fun getListOfEpisodes(episodesString: List<String>): String {
        val episodes = arrayListOf<Int>()
        episodesString.forEach {
            episodes.add(it.split("episode/")[1].toInt())
        }
        return removeCharactersForEpisodesList(episodes.toString())
    }

    private fun setLocation(singleLocation: SingleLocation) {
        with(binding) {
            tvName.text = singleLocation.name
            tvType.text = singleLocation.type
            tvDimention.text = singleLocation.dimension
            tvNumbersOfResidenst.text = singleLocation.residents.size.toString()
            skeletonLocation.showOriginal()
            skeletonLocation.setBackgroundColor(resources.getColor(R.color.background))
        }
    }


    private fun setDataToView(character: Character) = with(binding) {
        tvStatus.text = character.status
        tvSpecie.text = character.species
        tvGender.text = character.gender
        tvEpisodes.text = character.episode.size.toString()
        (requireActivity() as MainActivity).changeTitleToolbar(character.name)
        val statusColor = getColorStatus(character.status, requireContext())
        imageStatusSession.setColorFilter(statusColor)
        cardView.strokeColor = statusColor
        cardLocation.strokeColor = statusColor
        imageCharacter.loadCircularImage(
            model = character.image,
            borderSize = 8f,
            borderColor = statusColor
        )
        val colorResource = getColorStatusResource(character.status, requireContext())
        setStatusBarColor(colorResource)
        val colorDrawable = ColorDrawable(resources.getColor(colorResource))
        (requireActivity() as MainActivity).apply {
            changeDrawableAppBar(colorDrawable)
            changeToolbarColor(colorDrawable)
        }
    }


}