package com.rickandmortyorlando.orlando.features.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.fragment.findNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.databinding.FragmentLocationsBinding
import com.rickandmortyorlando.orlando.features.base.BaseFragment
import com.rickandmortyorlando.orlando.features.extensions.content
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationsFragmentWrapper :
    BaseFragment<FragmentLocationsBinding>(R.layout.fragment_locations) {


    override fun setUpUi() {
    }

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = getString(R.string.locations)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return content {
            val viewModel: LocationsViewModel = hiltViewModel()
            val locations = viewModel.getLocationsPagingSource.collectAsLazyPagingItems()
            LocationsScreen(locations = locations, clickOnItem = ::clickOnLocation)
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