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
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.example.domain.models.location.Location
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.components.skeletons.LocationSkeleton
import com.rickandmortyorlando.orlando.features.extensions.LoadState
import com.rickandmortyorlando.orlando.features.extensions.LoadStateConfig

@Composable
fun LocationsScreen(
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
    Column(modifier = Modifier
        .fillMaxWidth()
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
                    fontWeight = FontWeight.Bold
                )
                Text(modifier = Modifier.fillMaxWidth(), text = location.name)
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
fun LocationsScreenPreview() {
    LocationItem(
        location = Location(
            id = 0,
            name = stringResource(R.string.earth_c_137),
            url = "",
            dimension = "",
            created = "",
            type = stringResource(R.string.planet2)
        ),
        clickOnItem = {})
}
