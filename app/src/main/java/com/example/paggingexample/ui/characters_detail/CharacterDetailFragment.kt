package com.example.paggingexample.ui.characters_detail

import android.os.Bundle
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.view.marginStart
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.paggingexample.R
import com.example.paggingexample.data.models.character.Character
import com.example.paggingexample.data.state.ApiState
import com.example.paggingexample.databinding.FragmentCharacterDetailBinding
import com.example.paggingexample.ui.base.BaseFragment
import com.example.paggingexample.ui.extensions.click
import com.example.paggingexample.ui.extensions.loadImage
import com.example.paggingexample.ui.extensions.setMargins
import com.example.paggingexample.ui.extensions.showToast
import com.example.paggingexample.ui.main.AlertDialogs
import com.example.paggingexample.ui.main.AlertDialogs.Companion.ERROR_MESSAGE
import com.example.paggingexample.utils.getColorStatus
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CharacterDetailFragment :
    BaseFragment<FragmentCharacterDetailBinding>(R.layout.fragment_character_detail) {

    private val viewModel: CharacterDetailViewModel by viewModels()
    private val args: CharacterDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        observerViewModel()
    }

    override fun setUpUi() {
        viewModel.getCharacter(args.charcaterId.toString())
        with(binding) {
            skeletonInfo.showSkeleton()
            skeletonLocation.showSkeleton()
            toolbarLayout.toolbarBack.click {
                findNavController().popBackStack()
            }
            binding.tvEpisodes.click {
                requireContext().showToast("Go to episodies view and show episodios of the user")
            }
        }
    }

    override fun observerViewModel() {
        super.observerViewModel()
        viewModel.characterResponse.observe(viewLifecycleOwner) { apiState ->
            //binding.progress.isVisible = apiState is ViewModelResult.Loading
            when (apiState) {
                is ApiState.Success -> {
                    apiState.data?.let { character ->
                        viewModel.getSingleLocation(
                            character.location.url.last().toString().toInt()
                        )
                        with(binding) {
                            skeletonInfo.showOriginal()
                            skeletonInfo.setBackgroundColor(resources.getColor(R.color.background))
                            setDataToView(character)
                            skeletonInfo.setMargins(0, 0, 0, 0)
                        }
                    }
                }
                is ApiState.Error -> {
                    val dialog = AlertDialogs(ERROR_MESSAGE, "Error al obtener datos")
                    activity?.let { dialog.show(it.supportFragmentManager, "alertMessage") }
                    findNavController().popBackStack()
                }
                is ApiState.ErrorNetwork -> {
                    val dialog = AlertDialogs(ERROR_MESSAGE, "Verifica tu conexion de internet")
                    activity?.let { dialog.show(it.supportFragmentManager, "alertMessage") }
                }
                else -> {}
            }
        }
        viewModel.locationResponse.observe(viewLifecycleOwner) { apiState ->
            when (apiState) {
                is ApiState.Success -> {
                    with(binding) {
                        tvName.text = apiState.data?.name ?: ""
                        tvType.text = apiState.data?.type ?: ""
                        tvDimention.text = apiState.data?.dimension ?: ""
                        tvNumbersOfResidenst.text = apiState.data?.residents?.size.toString()
                        skeletonLocation.showOriginal()
                        skeletonLocation.setBackgroundColor(resources.getColor(R.color.background))
                        skeletonLocation.setMargins(0, 0, 0, 0)
                    }
                }
                is ApiState.Error -> {
                    val dialog = AlertDialogs(ERROR_MESSAGE, "Error al obtener datos")
                    activity?.let { dialog.show(it.supportFragmentManager, "alertMessage") }
                    findNavController().popBackStack()
                }
                is ApiState.ErrorNetwork -> {
                    val dialog = AlertDialogs(ERROR_MESSAGE, "Verifica tu conexion de internet")
                    activity?.let { dialog.show(it.supportFragmentManager, "alertMessage") }
                }
                else -> {}
            }
        }
    }

    private fun setDataToView(character: Character) {
        with(binding) {
            imageCharacter.loadImage(character.image)
            tvStatus.text = character.status
            tvSpecie.text = character.species
            tvGender.text = character.gender
            tvEpisodes.text = character.episode.size.toString()
            toolbarLayout.toolbarTitle.text = character.name
            imageStatusSession.setColorFilter(getColorStatus(character.status, requireContext()))
            cardView.strokeColor = getColorStatus(character.status, requireContext())
            cardLocation.strokeColor = getColorStatus(character.status, requireContext())
            setDrawableImage(character.status)
        }
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