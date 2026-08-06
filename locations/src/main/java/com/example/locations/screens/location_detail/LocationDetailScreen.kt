package com.example.locations.screens.location_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.core.navigation.AppNavigationRoutes
import com.example.core.ui.base.BaseComposeScreen
import com.example.core.ui.components.ErrorScreen
import com.example.core.ui.components.ItemCharacter
import com.example.core.ui.components.ToolbarConfiguration
import com.example.core.ui.state.BaseViewState
import com.example.domain.models.location.Location
import com.example.domain.models.location.getPairInfoLocation
import com.example.locations.components.LocationDetailSkeleton
import com.example.locations.R
import kotlinx.coroutines.flow.collectLatest


@Composable
fun LocationDetailRoute(navController: NavController, locationId: Int) {
    val locationDetailViewModel: LocationDetailViewModel =
        hiltViewModel(creationCallback = { factory: LocationDetailViewModelFactory ->
            factory.create(locationId)
        })
    LaunchedEffect(Unit) {
        locationDetailViewModel.effects.collectLatest {
            when (it) {
                is LocationDetailEffects.NavigateToCharacterDetail -> {
                    navController.navigate(
                        AppNavigationRoutes.CharactersDetailRoute(id = it.characterId)
                    )
                }
            }
        }
    }
    val state = locationDetailViewModel.state.collectAsState()
    LocationDetailScreen(
        viewState = state.value,
        clickOnCharacter = { id ->
            locationDetailViewModel.onEvents(LocationDetailEvents.OnCharacterClicked(id))
        },
        clickOnBack = {
            navController.navigateUp()
        }
    )
}

@Composable
private fun LocationDetailScreen(
    viewState: BaseViewState<LocationDetailUiState>,
    clickOnCharacter: (characterId: Int) -> Unit,
    clickOnBack: () -> Unit
) {
    when (viewState) {

        is BaseViewState.Loading -> {
            BaseComposeScreen(
                toolbarConfiguration = ToolbarConfiguration(
                    title = stringResource(R.string.location),
                    clickOnBackButton = { clickOnBack() }
                )
            ) {
                LocationDetailSkeleton()
            }
        }

        is BaseViewState.Content -> {
            BaseComposeScreen(
                toolbarConfiguration = ToolbarConfiguration(
                    title = viewState.result.location.name,
                    clickOnBackButton = { clickOnBack() }
                )
            ) {
                LocationDetailScreenContent(
                    uiState = viewState.result,
                    clickOnCharacter = { id -> clickOnCharacter(id) }
                )
            }
        }

        is BaseViewState.Error -> {
            ErrorScreen()
        }
    }

}


@Composable
private fun LocationDetailScreenContent(
    modifier: Modifier = Modifier,
    uiState: LocationDetailUiState,
    clickOnCharacter: (characterId: Int) -> Unit
) {
    Column(modifier = modifier) {
        ItemInfoLocation(location = uiState.location)
        LazyColumn {
            items(uiState.characters) { character ->
                ItemCharacter(
                    modifier = Modifier.fillMaxWidth(),
                    id = character.id,
                    image = character.image,
                    name = character.name,
                    status = character.status,
                    species = character.species,
                    clickOnItem = { id -> clickOnCharacter(id) }
                )
            }
        }
    }
}


@Composable
private fun ItemInfoLocation(
    modifier: Modifier = Modifier,
    location: Location
) {
    Column(Modifier.fillMaxWidth()) {
        location.getPairInfoLocation().forEach { infoItemLocation ->
            Card(
                modifier
                    .padding(8.dp)
                    .background(Color.White)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(8.dp),
                        text = infoItemLocation.first
                    )
                    Text(
                        modifier =
                            Modifier.padding(8.dp),
                        text = infoItemLocation.second
                    )
                }
            }
        }
    }
}