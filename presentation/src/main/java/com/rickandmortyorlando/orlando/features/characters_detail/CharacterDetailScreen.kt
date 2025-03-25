package com.rickandmortyorlando.orlando.features.characters_detail

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.models.characters.Character
import com.example.domain.models.location.Location
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.components.ItemCharacter
import com.rickandmortyorlando.orlando.theme.Alive

@Composable
fun CharacterDetailScreen(
    characters: List<Character>,
    clickOnCharacter: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .border(width = 2.dp, color = Color.Black, shape = CircleShape),
            painter = painterResource(R.drawable.dinosaur),
            contentDescription = null
        )
        Spacer(Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        ) {
            CharacterDetail(character = Character.emptyCharacter())
            Spacer(Modifier.height(32.dp))
            Text(
                text = stringResource(R.string.last_seen_location),
                fontWeight = FontWeight.Bold
            )
            LocationDetails(location = Location.mockLocation())
            Spacer(Modifier.height(32.dp))
            Text(
                text = stringResource(R.string.last_seen_location_residents),
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            LazyColumn {  //// Add a new view to show character in horizontal
                items(characters) { character ->
                    ItemCharacter(
                        character = character,
                        clickOnItem = { clickOnCharacter(character.id) })
                }
            }
        }
    }
}

@Composable
private fun CharacterDetail(character: Character) {
    Row {
        Text(text = stringResource(R.string.status), fontWeight = FontWeight.Bold)
        Spacer(Modifier.width(8.dp))
        Canvas(
            modifier = Modifier.size(14.dp),
            onDraw = {
                drawCircle(color = Alive)
            }
        )
        Spacer(Modifier.width(8.dp))
        Text(character.status)
    }
    Spacer(Modifier.height(16.dp))
    Row {
        Text(text = stringResource(R.string.species), fontWeight = FontWeight.Bold)
        Spacer(Modifier.width(8.dp))
        Text(character.species)
    }
    Spacer(Modifier.height(16.dp))
    Row {
        Text(text = stringResource(R.string.gender), fontWeight = FontWeight.Bold)
        Spacer(Modifier.width(8.dp))
        Text(character.gender)
    }
}

@Composable
private fun LocationDetails(location: Location) {
    Spacer(Modifier.height(16.dp))
    Row {
        Text(text = stringResource(R.string.name), fontWeight = FontWeight.Bold)
        Spacer(Modifier.width(8.dp))
        Text(location.name)
    }
    Spacer(Modifier.height(16.dp))
    Row {
        Text(text = stringResource(R.string.type), fontWeight = FontWeight.Bold)
        Spacer(Modifier.width(8.dp))
        Text(location.type)
    }
    Spacer(Modifier.height(16.dp))
    Row {
        Text(text = stringResource(R.string.dimension), fontWeight = FontWeight.Bold)
        Spacer(Modifier.width(8.dp))
        Text(location.dimension)
    }
    Spacer(Modifier.height(16.dp))
    Row {
        Text(text = stringResource(R.string.numbers_of_residents), fontWeight = FontWeight.Bold)
        Spacer(Modifier.width(8.dp))
        Text("3")
    }
}

@Composable
@Preview(showBackground = true)
private fun CharacterDetailScreenPreview(modifier: Modifier = Modifier) {
    CharacterDetailScreen(
        characters = Character.getCharacters(8),
        clickOnCharacter = {},
    )
}

