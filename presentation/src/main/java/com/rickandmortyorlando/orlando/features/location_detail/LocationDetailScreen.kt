package com.rickandmortyorlando.orlando.features.location_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.data.model.location.SingleLocation
import com.example.data.model.location.toLocation
import com.example.domain.models.characters.Character
import com.example.domain.models.location.Location
import com.example.domain.models.location.getPairInfoLocation
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.components.ItemCharacter


@Composable
fun LocationDetailScreen(
    idLocation: Int,
    locationDetailViewModel: LocationDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(locationDetailViewModel) {
        locationDetailViewModel.getLocationInfo(locationId = idLocation)
    }
    val locationDetailState = locationDetailViewModel.state.collectAsState()

    when (locationDetailState.value) {
        is LocationState.Error -> {
            Text(text = "Error")
        }

        is LocationState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(64.dp),
                    color = colorResource(R.color.progress_color)
                )
            }
        }

        is LocationState.Success -> {
            val location = (locationDetailState.value as LocationState.Success).location
            val characters = (locationDetailState.value as LocationState.Success).character
            LocationDetailContent(singleLocation = location, characters = characters)
        }
    }
}

@Composable
fun LocationDetailContent(
    modifier: Modifier = Modifier,
    singleLocation: SingleLocation,
    characters: List<Character>
) {
    Column(modifier = modifier) {
        ItemInfoLocation(location = singleLocation.toLocation())
        LazyColumn {
            items(characters) { character ->
                ItemCharacter(character = character)
            }
        }
    }
}


@Composable
fun ItemInfoLocation(
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
fun LocationDetailScreenPreview(modifier: Modifier = Modifier) {
    //Todo add preview when we migrate models from data to domain
    ItemCharacter(
        character = Character(
            id = 0,
            image = "",
            name = "Rick",
            status = "Alive",
            species = "Human",
            urlLocation = "",
            episode = emptyList(),
            gender = "",
        )
    )
}