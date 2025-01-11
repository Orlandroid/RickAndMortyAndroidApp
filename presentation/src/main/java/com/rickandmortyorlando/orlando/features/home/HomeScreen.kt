package com.rickandmortyorlando.orlando.features.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.rickandmortyorlando.orlando.R


@Composable
fun HomeScreen(
    clickOnCharacters: () -> Unit,
    clickOnLocation: () -> Unit,
    clickOnEpisodes: () -> Unit
) {
    val charactersImage = painterResource(id = R.drawable.rick_and_morty)
    val episodesImage = painterResource(id = R.drawable.img_episode)
    val locationsImage = painterResource(id = R.drawable.img_location)

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (imageCharacter, imageEpisodes, imageLocations, toolbar) = createRefs()
        val firstGuideline = createGuidelineFromTop(0.33f)
        val secondGuideline = createGuidelineFromTop(0.67f)
        ImageCard(
            modifier = Modifier
                .padding(top = 12.dp, start = 12.dp, end = 12.dp)
                .fillMaxWidth()
                .constrainAs(imageCharacter) {
                    height = Dimension.fillToConstraints
                    top.linkTo(parent.top)
                    bottom.linkTo(firstGuideline)
                }, textOnCard = stringResource(
                R.string.characters
            ), painter = charactersImage
        ) {
            clickOnCharacters()
        }

        ImageCard(
            modifier = Modifier
                .padding(top = 12.dp, start = 12.dp, end = 12.dp)
                .constrainAs(imageEpisodes) {
                    height = Dimension.fillToConstraints
                    top.linkTo(firstGuideline)
                    bottom.linkTo(secondGuideline)
                }, textOnCard = stringResource(
                R.string.episodes
            ), painter = episodesImage
        ) {
            clickOnEpisodes()
        }

        ImageCard(
            modifier = Modifier
                .padding(top = 12.dp, bottom = 12.dp, start = 12.dp, end = 12.dp)
                .constrainAs(imageLocations) {
                    height = Dimension.fillToConstraints
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(secondGuideline)
                    bottom.linkTo(parent.bottom)
                }, textOnCard = stringResource(
                R.string.locations
            ), painter = locationsImage
        ) {
            clickOnLocation()
        }
    }


}

@Composable
fun ImageCard(
    textOnCard: String,
    modifier: Modifier = Modifier,
    painter: Painter,
    clickOnCard: () -> Unit = {}
) {
    Card(
        onClick = clickOnCard, modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ), border = BorderStroke(2.dp, Color.Black), shape = RoundedCornerShape(8.dp)
    ) {
        ConstraintLayout(Modifier.fillMaxSize()) {
            val (image, text) = createRefs()
            Image(painter = painter,
                contentDescription = null,
                modifier = Modifier.constrainAs(image) {
                    width = Dimension.matchParent
                    height = Dimension.matchParent
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(text.top)
                })
            Text(
                modifier = Modifier.constrainAs(text) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }, textAlign = TextAlign.Center, text = textOnCard.uppercase(), style = TextStyle(
                    color = Color.Black, fontSize = 32.sp, fontWeight = FontWeight.Bold
                )
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SimpleComposablePreview() {
    HomeScreen(
        clickOnCharacters = {},
        clickOnLocation = {},
        clickOnEpisodes = {}
    )
}