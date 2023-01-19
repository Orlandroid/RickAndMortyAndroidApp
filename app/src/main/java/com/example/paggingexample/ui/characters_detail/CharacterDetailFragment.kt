package com.example.paggingexample.ui.characters_detail

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.paggingexample.R
import com.example.paggingexample.data.models.remote.location.character.Character
import com.example.paggingexample.databinding.FragmentCharacterDetailBinding
import com.example.paggingexample.ui.base.BaseFragment
import com.example.paggingexample.ui.extensions.click
import com.example.paggingexample.ui.extensions.loadImage
import com.example.paggingexample.ui.extensions.observeApiResultGeneric
import com.example.paggingexample.ui.extensions.setMargins
import com.example.paggingexample.utils.getColorStatus
import com.example.paggingexample.utils.removeCharactersForEpisodesList
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
        toolbarLayout.toolbarBack.click {
            findNavController().popBackStack()
        }
        binding.tvEpisodes.click {
            val isSingleEpisode = idsOfEpisodesOfTheCharacter.contains(",")
            val action =
                CharacterDetailFragmentDirections.actionCharacterDetailFragmentToManyEpisodesFragment(
                    idsOfEpisodesOfTheCharacter, !isSingleEpisode
                )
            findNavController().navigate(action)
        }

    }

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
            viewModel.getSingleLocation(character.id)
        }
        observeApiResultGeneric(
            viewModel.locationResponse, shouldCloseTheViewOnApiError = true
        ) { singleLocation ->
            with(binding) {
                tvName.text = singleLocation.name
                tvType.text = singleLocation.type
                tvDimention.text = singleLocation.dimension
                tvNumbersOfResidenst.text = singleLocation.residents.size.toString()
                skeletonLocation.showOriginal()
                skeletonLocation.setBackgroundColor(resources.getColor(R.color.background))
                skeletonLocation.setMargins(0, 0, 0, 0)
            }
        }
    }

    private fun getListOfEpisodes(episodesString: List<String>): String {
        val episodes = arrayListOf<Int>()
        episodesString.forEach {
            episodes.add(it.split("episode/")[1].toInt())
        }
        return removeCharactersForEpisodesList(episodes.toString())
    }


    private fun setDataToView(character: Character) = with(binding) {
        tvStatus.text = character.status
        tvSpecie.text = character.species
        tvGender.text = character.gender
        tvEpisodes.text = character.episode.size.toString()
        toolbarLayout.toolbarTitle.text = character.name
        imageStatusSession.setColorFilter(getColorStatus(character.status, requireContext()))
        cardView.strokeColor = getColorStatus(character.status, requireContext())
        cardLocation.strokeColor = getColorStatus(character.status, requireContext())
        //setDrawableImage(character.status)
        imageCharacter.loadImage(character.image)
    }


    private fun setDrawableImage(status: String) {
        when (status) {
            "Alive" -> {
                binding.imageCharacter.setImageDrawable(resources.getDrawable(R.drawable.shape_image_alive))
            }
            "Dead" -> {
                binding.imageCharacter.setImageDrawable(resources.getDrawable(R.drawable.shape_image_dead))
            }
            "unknown" -> {
                binding.imageCharacter.setImageDrawable(resources.getDrawable(R.drawable.shape_image_unknown))
            }
        }
    }

}