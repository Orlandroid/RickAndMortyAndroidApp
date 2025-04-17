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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.models.characters.Character
import com.example.domain.models.location.Location
import com.example.domain.models.location.getPairInfoLocation
import com.rickandmortyorlando.orlando.components.ItemCharacter


@Composable
fun LocationDetailScreen(
    modifier: Modifier = Modifier,
    uiState: LocationDetailUiState,
    clickOnCharacter: (characterId: Int, name: String) -> Unit
) {
    Column(modifier = modifier) {
        ItemInfoLocation(location = uiState.location)
        LazyColumn {
            items(uiState.characters) { character ->
                ItemCharacter(
                    character = character,
                    clickOnItem = { id, name -> clickOnCharacter(character.id, name) })
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