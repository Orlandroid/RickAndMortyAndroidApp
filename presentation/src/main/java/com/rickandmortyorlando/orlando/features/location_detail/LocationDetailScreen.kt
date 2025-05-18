package com.rickandmortyorlando.orlando.features.location_detail

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.domain.models.characters.Character
import com.example.domain.models.location.Location
import com.example.domain.models.location.getPairInfoLocation
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.app_navigation.AppNavigationRoutes
import com.rickandmortyorlando.orlando.components.ErrorScreen
import com.rickandmortyorlando.orlando.components.ItemCharacter
import com.rickandmortyorlando.orlando.components.ToolbarConfiguration
import com.rickandmortyorlando.orlando.components.skeletons.LocationDetailSkeleton
import com.rickandmortyorlando.orlando.features.base.BaseComposeScreen
import com.rickandmortyorlando.orlando.state.BaseViewState


@Composable
fun LocationDetailRoute(navController: NavController, locationId: Int) {
    val locationDetailViewModel: LocationDetailViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        locationDetailViewModel.getLocationDetail(locationId = locationId)
    }
    val state = locationDetailViewModel.state.collectAsState()
    LocationDetailScreen(
        viewState = state.value,
        clickOnCharacter = { id ->
            navController.navigate(
                AppNavigationRoutes.CharactersDetailRoute(id = id)
            )
        },
        clickOnBack = {
            navController.navigateUp()
        }
    )
}

@Composable
fun LocationDetailScreen(
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
fun LocationDetailScreenContent(
    modifier: Modifier = Modifier,
    uiState: LocationDetailUiState,
    clickOnCharacter: (characterId: Int) -> Unit
) {
    Column(modifier = modifier.background(colorResource(R.color.background))) {
        ItemInfoLocation(location = uiState.location)
        LazyColumn {
            items(uiState.characters) { character ->
                ItemCharacter(
                    character = character,
                    clickOnItem = { id -> clickOnCharacter(id) })
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

@Composable
@Preview(showBackground = true, widthDp = 350)
private fun LocationDetailScreenPreview(modifier: Modifier = Modifier) {
    ItemCharacter(
        character = Character.mockCharacter()
    )
}