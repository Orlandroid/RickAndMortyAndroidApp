package com.example.paggingexample.ui.location_detail

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.paggingexample.R
import com.example.paggingexample.databinding.FragmentLocationDetailBinding
import com.example.paggingexample.ui.base.BaseFragment
import com.example.paggingexample.ui.characters.CharacterAdapter
import com.example.paggingexample.ui.characters.CharacterViewModel
import com.example.paggingexample.ui.extensions.click
import com.example.paggingexample.ui.extensions.observeApiResultGeneric
import com.example.paggingexample.ui.extensions.visible
import com.example.paggingexample.ui.locations.LocationsViewModel
import com.example.paggingexample.utils.getListOfNumbersFromUrlWithPrefix
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LocationDetailFragment :
    BaseFragment<FragmentLocationDetailBinding>(R.layout.fragment_location_detail) {


    private val locationViewModel: LocationsViewModel by viewModels()
    private val characterViewModel: CharacterViewModel by viewModels()
    private var adapter: CharacterAdapter? = null
    private val args: LocationDetailFragmentArgs by navArgs()


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
            characterViewModel.getManyCharacters(
                getListOfNumbersFromUrlWithPrefix(
                    it.residents,
                    "character"
                )
            )
        }
        observeApiResultGeneric(
            characterViewModel.manyCharactersResponse,
            hasProgressTheView = true,
            shouldCloseTheViewOnApiError = true
        ) {
            with(binding) {
                locationType.visible()
                locationName.visible()
            }
            adapter?.setData(it)
        }
    }

}