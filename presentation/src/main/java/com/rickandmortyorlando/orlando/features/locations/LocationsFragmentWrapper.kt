package com.rickandmortyorlando.orlando.features.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.fragment.findNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.components.ToolbarConfiguration
import com.rickandmortyorlando.orlando.features.base.BaseComposeScreen
import com.rickandmortyorlando.orlando.features.extensions.content
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationsFragmentWrapper : Fragment(R.layout.fragment_locations) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return content {
            val viewModel: LocationsViewModel = hiltViewModel()
            val locations = viewModel.locations.collectAsLazyPagingItems()
            BaseComposeScreen(
                toolbarConfiguration = ToolbarConfiguration(
                    title = stringResource(R.string.locations),
                    clickOnBackButton = {
                        findNavController().navigateUp()
                    }
                )
            ) {
                LocationScreen(locations = locations, clickOnItem = ::clickOnLocation, onNavigateBack = {})
            }
        }
    }


    private fun clickOnLocation(locationId: Int) {
        findNavController().navigate(
            LocationsFragmentWrapperDirections.actionLocationsFragmentWrapperToLocationDetailScreenWrapper(
                locationDetail = locationId
            )
        )
    }

}