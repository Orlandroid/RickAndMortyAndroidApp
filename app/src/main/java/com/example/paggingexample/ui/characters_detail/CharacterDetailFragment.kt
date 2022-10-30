package com.example.paggingexample.ui.characters_detail

import android.os.Bundle
import android.view.View
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
import com.example.paggingexample.ui.extensions.showToast
import com.example.paggingexample.ui.main.AlertDialogs
import com.example.paggingexample.ui.main.AlertDialogs.Companion.ERROR_MESSAGE
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
                        setDataToView(character)
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
            tvGender.text = character.species
            tvEpisodes.text = character.episode.size.toString()
            toolbarLayout.toolbarTitle.text = character.name
        }
    }


}