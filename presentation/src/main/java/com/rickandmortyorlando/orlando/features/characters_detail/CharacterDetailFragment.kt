package com.rickandmortyorlando.orlando.features.characters_detail

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.data.model.location.SingleLocation
import com.example.domain.models.characters.Character
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.databinding.FragmentCharacterDetailBinding
import com.rickandmortyorlando.orlando.features.base.BaseFragment
import com.rickandmortyorlando.orlando.features.extensions.click
import com.rickandmortyorlando.orlando.features.extensions.gone
import com.rickandmortyorlando.orlando.features.extensions.observeApiResultGeneric
import com.rickandmortyorlando.orlando.features.extensions.setMargins
import com.rickandmortyorlando.orlando.features.extensions.setStatusBarColor
import com.rickandmortyorlando.orlando.utils.getColorStatus
import com.rickandmortyorlando.orlando.utils.getColorStatusResource
import com.rickandmortyorlando.orlando.utils.getNumberFromUrWithPrefix
import com.rickandmortyorlando.orlando.utils.loadCircularImage
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CharacterDetailFragment :
    BaseFragment<FragmentCharacterDetailBinding>(R.layout.fragment_character_detail) {

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(showToolbar = true)

    private val viewModel: CharacterDetailViewModel by viewModels()
//    private val args: CharacterDetailFragmentArgs by navArgs()
    private var idsOfEpisodesOfTheCharacter = ""
    private var mCharacter: Character? = null
    private var mLocation: SingleLocation? = null

    override fun setUpUi() = binding.tvEpisodes.click {
        navigateToEpisodes()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel.getCharacter(args.charcaterId.toString())
    }

    override fun onResume() {
        super.onResume()
        restoreCharacterAndLocation()
    }

    private fun restoreCharacterAndLocation() {
        mCharacter?.let { character ->
            with(binding) {
                skeletonInfo.setBackgroundColor(resources.getColor(R.color.background))
                skeletonInfo.setMargins(0, 0, 0, 0)
            }
            setDataToView(character)
        }
        mLocation?.let { location ->
            setLocation(location)
        }
    }

    private fun navigateToEpisodes() {
        val isSingleEpisode = idsOfEpisodesOfTheCharacter.contains(",")
//        val action =
//            CharacterDetailFragmentDirections.actionCharacterDetailFragmentToManyEpisodesFragment(
//                idsOfEpisodesOfTheCharacter, !isSingleEpisode
//            )
//        findNavController().navigate(action)
    }


    override fun observerViewModel() {
        super.observerViewModel()
        observeCharacterResponse()
        observeLocationResponse()
    }

    private fun observeCharacterResponse() {
//        observeApiResultGeneric(
//            viewModel.characterResponse,
//            shouldCloseTheViewOnApiError = true,
//            onLoading = {
//                with(binding) {
//                    skeletonInfo.showSkeleton()
//                    skeletonLocation.showSkeleton()
//                }
//            }
//        ) { character ->
//            mCharacter = character
//            with(binding) {
//                skeletonInfo.showOriginal()
//                skeletonInfo.setBackgroundColor(resources.getColor(R.color.background))
//                skeletonInfo.setMargins(0, 0, 0, 0)
//                setDataToView(character)
//                idsOfEpisodesOfTheCharacter = getListOfEpisodes(character.episode)
//            }
//            if (characterHasLocation(character)) {
//                viewModel.getSingleLocation(
//                    getNumberFromUrWithPrefix(
//                        character.urlLocation, "location"
//                    )
//                )
//                return@observeApiResultGeneric
//            }
//            binding.skeletonLocation.showOriginal()
//            binding.skeletonLocation.gone()
//
//        }
    }

    private fun observeLocationResponse() {
//        observeApiResultGeneric(
//            viewModel.locationResponse, shouldCloseTheViewOnApiError = true
//        ) { singleLocation ->
//            mLocation = singleLocation
//            setLocation(singleLocation)
//        }
    }

    private fun characterHasLocation(character: Character): Boolean {
        return character.urlLocation.isNotEmpty()
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
            model = character.image, borderSize = 8f, borderColor = statusColor
        )
        val colorResource = getColorStatusResource(character.status)
        setStatusBarColor(colorResource)
        val colorDrawable = ColorDrawable(resources.getColor(colorResource))
        (requireActivity() as MainActivity).apply {
            changeDrawableAppBar(colorDrawable)
            changeToolbarColor(colorDrawable)
        }
    }


}