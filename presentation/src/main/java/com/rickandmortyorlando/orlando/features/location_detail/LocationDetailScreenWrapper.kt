package com.rickandmortyorlando.orlando.features.location_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.components.ErrorScreen
import com.rickandmortyorlando.orlando.components.skeletons.LocationDetailSkeleton
import com.rickandmortyorlando.orlando.databinding.FragmentLocationDetailBinding
import com.rickandmortyorlando.orlando.features.base.BaseFragment
import com.rickandmortyorlando.orlando.features.extensions.content
import com.rickandmortyorlando.orlando.state.BaseViewState

class LocationDetailScreenWrapper :
    BaseFragment<FragmentLocationDetailBinding>(R.layout.fragment_location_detail) {

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        toolbarTitle = getString(R.string.location),
        showToolbar = true
    )


    private val args: LocationDetailScreenWrapperArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return content {
            val locationDetailViewModel: LocationDetailViewModel = hiltViewModel()
            LaunchedEffect(locationDetailViewModel) {
                locationDetailViewModel.getLocationInfo(locationId = args.locationDetail)
            }
            val state = locationDetailViewModel.state.collectAsState()

            when (val currentState = state.value) {

                is BaseViewState.Loading -> {
                    LocationDetailSkeleton()
                }

                is BaseViewState.Content -> {
                    LocationDetailScreen(
                        uiState = currentState.result,
                        clickOnCharacter = { clickOnCharacter(it) }
                    )
                }

                is BaseViewState.Error -> {
                    ErrorScreen()
                }
            }
        }
    }

    private fun clickOnCharacter(characterId: Int) {
        findNavController().navigate(
            LocationDetailScreenWrapperDirections.navigationToCharacterDetailWrapper(
                characterId
            )
        )
    }

    override fun setUpUi() {

    }
}