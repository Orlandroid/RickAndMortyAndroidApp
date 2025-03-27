package com.rickandmortyorlando.orlando.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.domain.models.characters.Character
import com.rickandmortyorlando.orlando.utils.getColorStatus

@Composable
fun ItemCharacter(
    modifier: Modifier = Modifier,
    character: Character,
    clickOnItem: (characterId: Int) -> Unit = {}
) {
    val colorStatus = character.status.getColorStatus()
    Card(
        onClick = { clickOnItem(character.id) },
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, colorStatus),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .size(100.dp),
                model = character.image,
                contentDescription = "ImageStaff",
                loading = { CircularProgressIndicator(Modifier.padding(16.dp)) }
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    text = character.name
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    val colorStatusColor = character.status.getColorStatus()
                    Canvas(
                        modifier = Modifier.size(16.dp),
                        onDraw = {
                            drawCircle(color = colorStatusColor)
                        }
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier,
                        text = character.status
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        textAlign = TextAlign.Center,
                        modifier = Modifier,
                        text = "-"
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier,
                        text = character.species
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ItemCharacterPreview(modifier: Modifier = Modifier) {
    ItemCharacter(
        character = Character.mockCharacter()
    )
}