package com.rickandmortyorlando.paggingexample.ui.location_detail

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rickandmortyorlando.paggingexample.R
import com.rickandmortyorlando.paggingexample.databinding.FragmentLocationDetailBinding
import com.rickandmortyorlando.paggingexample.ui.base.BaseFragment
import com.rickandmortyorlando.paggingexample.ui.characters.CharacterAdapter
import com.rickandmortyorlando.paggingexample.ui.characters.CharacterViewModel
import com.rickandmortyorlando.paggingexample.ui.characters_detail.CharacterDetailViewModel
import com.rickandmortyorlando.paggingexample.ui.extensions.click
import com.rickandmortyorlando.paggingexample.ui.extensions.observeApiResultGeneric
import com.rickandmortyorlando.paggingexample.ui.extensions.visible
import com.rickandmortyorlando.paggingexample.ui.locations.LocationsViewModel
import com.rickandmortyorlando.paggingexample.utils.getListOfNumbersFromUrlWithPrefix
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LocationDetailFragment :
    BaseFragment<FragmentLocationDetailBinding>(R.layout.fragment_location_detail) {


    private val locationViewModel: LocationsViewModel by viewModels()
    private val characterViewModel: CharacterViewModel by viewModels()
    private val characterDetailViewModel: CharacterDetailViewModel by viewModels()
    private var adapter: CharacterAdapter? = null
    private val args: LocationDetailFragmentArgs by navArgs()
    private var idsOfCharacters = ""


    override fun setUpUi() = with(binding) {
        toolbarLayout.toolbarBack.click {
            findNavController().popBackStack()
        }
        adapter = CharacterAdapter()
        recycler.adapter = adapter
        locationViewModel.getSingleLocation(args.idLocation)
    }

    override fun observerViewModel() {
        super.observerViewModel()
        observeApiResultGeneric(
            locationViewModel.singleLocationResponse,
            hasProgressTheView = true,
            shouldCloseTheViewOnApiError = true
        ) {
            with(binding) {
                toolbarLayout.toolbarTitle.text = it.name
                locationType.text = it.type
                locationName.text = it.name
            }
            idsOfCharacters = getListOfIdsOfCharacters(it.residents)
            if (isSingleCharacter()) {
                characterDetailViewModel.getCharacter(idsOfCharacters)
            } else {
                characterViewModel.getManyCharacters(idsOfCharacters)
            }
        }
        observeApiResultGeneric(
            characterViewModel.manyCharactersResponse,
            hasProgressTheView = true,
            shouldCloseTheViewOnApiError = true
        ) {
            showLocationInfo()
            adapter?.setData(it)
        }
        observeApiResultGeneric(
            characterDetailViewModel.characterResponse,
            hasProgressTheView = true,
            shouldCloseTheViewOnApiError = true
        ) { character ->
            showLocationInfo()
            adapter?.setData(listOf(character))
        }
    }

    private fun showLocationInfo() {
        with(binding) {
            locationType.visible()
            locationName.visible()
        }
    }

    private fun isSingleCharacter() = !idsOfCharacters.contains(",")

    private fun getListOfIdsOfCharacters(idsInUrl: List<String>): String {
        return getListOfNumbersFromUrlWithPrefix(
            idsInUrl,
            "character"
        )
    }

}