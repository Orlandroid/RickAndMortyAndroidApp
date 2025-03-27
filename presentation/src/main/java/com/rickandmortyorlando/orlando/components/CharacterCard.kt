package com.rickandmortyorlando.orlando.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.domain.models.characters.Character

@Composable
fun CharacterCard(
    character: Character,
    onCharacterClick: ((Int) -> Unit)? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .wrapContentWidth()
            .clickable { onCharacterClick?.invoke(character.id) }) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .size(112.dp)
                .clip(CircleShape),
            model = character.image,
            contentDescription = "ImageStaff",
            loading = { CircularProgressIndicator(Modifier.padding(16.dp)) }
        )
        Text(
            modifier = Modifier
                .padding(top = 8.dp),
            text = character.name
        )
    }

}

@Preview
@Composable
private fun CharacterCardPreview() {
    CharacterCard(
        character = Character.mockCharacter(),
        onCharacterClick = {}
    )
}
