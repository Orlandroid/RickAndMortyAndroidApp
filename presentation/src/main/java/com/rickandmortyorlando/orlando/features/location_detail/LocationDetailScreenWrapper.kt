package com.rickandmortyorlando.orlando.features.location_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.components.ErrorScreen
import com.rickandmortyorlando.orlando.components.ToolbarConfiguration
import com.rickandmortyorlando.orlando.components.skeletons.LocationDetailSkeleton
import com.rickandmortyorlando.orlando.features.base.BaseComposeScreen
import com.rickandmortyorlando.orlando.features.extensions.content
import com.rickandmortyorlando.orlando.state.BaseViewState

class LocationDetailScreenWrapper : Fragment(R.layout.fragment_location_detail) {


    private val args: LocationDetailScreenWrapperArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return content {
            val locationDetailViewModel: LocationDetailViewModel = hiltViewModel()
            LaunchedEffect(Unit) {
                locationDetailViewModel.getLocationDetail(locationId = args.locationDetail)
            }
            val state = locationDetailViewModel.state.collectAsState()

            BaseComposeScreen(
                toolbarConfiguration = ToolbarConfiguration(
                    title = stringResource(R.string.location),
                    clickOnBackButton = { findNavController().navigateUp() }
                )
            ) {
                when (val currentState = state.value) {

                    is BaseViewState.Loading -> {
                        LocationDetailSkeleton()
                    }

                    is BaseViewState.Content -> {
                        LocationDetailScreen(
                            uiState = currentState.result,
                            clickOnCharacter = { id, name -> clickOnCharacter(id, name) }
                        )
                    }

                    is BaseViewState.Error -> {
                        ErrorScreen()
                    }
                }
            }

        }
    }

    private fun clickOnCharacter(characterId: Int, name: String) {
        findNavController().navigate(
            LocationDetailScreenWrapperDirections.navigationToCharacterDetailWrapper(
                characterId,
                name
            )
        )
    }
}