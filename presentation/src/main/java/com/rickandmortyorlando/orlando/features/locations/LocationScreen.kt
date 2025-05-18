package com.rickandmortyorlando.orlando.features.locations


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.domain.models.location.Location
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.app_navigation.AppNavigationRoutes
import com.rickandmortyorlando.orlando.components.ToolbarConfiguration
import com.rickandmortyorlando.orlando.components.skeletons.LocationSkeleton
import com.rickandmortyorlando.orlando.features.base.BaseComposeScreen
import com.rickandmortyorlando.orlando.features.extensions.LoadState
import com.rickandmortyorlando.orlando.features.extensions.LoadStateConfig
import kotlinx.coroutines.flow.flowOf


@Composable
fun LocationRoute(navController: NavController) {
    val viewModel: LocationsViewModel = hiltViewModel()
    val locations = viewModel.locations.collectAsLazyPagingItems()
    LocationScreen(
        locations = locations,
        clickOnItem = {
            navController.navigate(AppNavigationRoutes.LocationDetailRoute(it))
        },
        onNavigateBack = {
            navController.navigateUp()
        }
    )
}


@Composable
fun LocationScreen(
    locations: LazyPagingItems<Location>,
    clickOnItem: (locationId: Int) -> Unit,
    onNavigateBack: () -> Unit
) {
    BaseComposeScreen(
        toolbarConfiguration = ToolbarConfiguration(
            title = stringResource(R.string.locations),
            clickOnBackButton = onNavigateBack
        )
    ) {
        LocationsScreenContent(locations = locations, clickOnItem = clickOnItem)
    }
}

@Composable
private fun LocationsScreenContent(
    locations: LazyPagingItems<Location>,
    clickOnItem: (locationId: Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(
            count = locations.itemCount,
            key = locations.itemKey { it.id }
        ) { index ->
            locations[index]?.let { location ->
                LocationItem(
                    location = location,
                    clickOnItem = clickOnItem
                )
            }
        }
        item {
            locations.LoadState(
                Modifier.fillParentMaxSize(),
                config = LoadStateConfig(
                    initialLoading = {
                        for (i in 0..15) {
                            LocationSkeleton()
                        }
                    }
                )
            )
        }
    }
}


@Composable
private fun LocationItem(
    location: Location,
    clickOnItem: (locationId: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(R.color.white))
            .clickable { clickOnItem(location.id) }) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = location.type,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.black)
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = location.name,
                    color = colorResource(R.color.black)
                )
            }
            Icon(
                modifier = Modifier.padding(end = 16.dp),
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null
            )
        }
        HorizontalDivider(
            modifier = Modifier
                .padding(top = 8.dp)
                .background(colorResource(R.color.gris))
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LocationsScreenPreview() {
    val items = flowOf(
        PagingData.from(
            listOf(
                Location.mockLocation(),
                Location.mockLocation()
            ),
            sourceLoadStates =
                LoadStates(
                    refresh = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false),
                    prepend = LoadState.NotLoading(false)
                )
        )
    ).collectAsLazyPagingItems()
    LocationsScreenContent(locations = items, clickOnItem = {})
}
