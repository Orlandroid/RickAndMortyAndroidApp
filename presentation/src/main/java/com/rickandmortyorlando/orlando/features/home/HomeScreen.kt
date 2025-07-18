package com.rickandmortyorlando.orlando.features.home

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.app_navigation.AppNavigationRoutes
import com.rickandmortyorlando.orlando.components.ToolbarConfiguration
import com.rickandmortyorlando.orlando.features.base.BaseComposeScreen
import kotlinx.coroutines.flow.collectLatest


@Composable
fun HomeRote(navController: NavController) {
    val viewModel: HomeViewModel = hiltViewModel()
    LaunchedEffect(viewModel) {
        viewModel.effects.collectLatest {
            when (it) {
                HomeEffects.NavigateToCharacters -> {
                    navController.navigate(AppNavigationRoutes.CharactersRoute)
                }

                HomeEffects.NavigateToEpisodes -> {
                    navController.navigate(AppNavigationRoutes.EpisodesRoute)
                }

                HomeEffects.NavigateToLocations -> {
                    navController.navigate(AppNavigationRoutes.LocationsRoute)
                }

                HomeEffects.NavigateToSettings -> {
                    navController.navigate(AppNavigationRoutes.SettingsRoute)
                }
            }
        }
    }
    HomeScreen(onEvents = viewModel::onEvents)
}

@Composable
private fun HomeScreen(
    onEvents: (event: HomeEvents) -> Unit
) {
    BaseComposeScreen(
        toolbarConfiguration = ToolbarConfiguration(
            showBackIcon = false,
            title = stringResource(R.string.home),
            actions = {
                IconButton(
                    onClick = {
                        onEvents(HomeEvents.ClickOnSettings)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Settings",
                        tint = Color.White
                    )
                }
            }
        )
    ) {
        HomeScreenContent(onEvents = onEvents)
    }
}

@Composable
private fun HomeScreenContent(onEvents: (event: HomeEvents) -> Unit) {
    val paddingValues = PaddingValues(top = 12.dp, start = 12.dp, end = 12.dp)
    Column(Modifier.fillMaxSize()) {
        ImageCard(
            modifier = Modifier
                .padding(paddingValues)
                .weight(1F),
            imageCard = painterResource(id = R.drawable.rick_and_morty),
            textOnCard = R.string.characters
        ) {
            onEvents(HomeEvents.ClickOnCharacters)
        }
        ImageCard(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .weight(1F),
            imageCard = painterResource(id = R.drawable.img_episode),
            textOnCard = R.string.episodes
        ) {
            onEvents(HomeEvents.ClickOnEpisodes)
        }
        ImageCard(
            modifier = Modifier
                .padding(top = 12.dp, start = 12.dp, end = 12.dp, bottom = 12.dp)
                .fillMaxWidth()
                .weight(1F),
            imageCard = painterResource(id = R.drawable.img_location),
            textOnCard = R.string.locations
        ) {
            onEvents(HomeEvents.ClickOnLocations)
        }
    }
}

@Composable
private fun ImageCard(
    modifier: Modifier = Modifier,
    imageCard: Painter,
    @StringRes
    textOnCard: Int,
    clickOnCard: () -> Unit
) {
    Card(
        onClick = clickOnCard,
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        border = BorderStroke(2.dp, Color.Black),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                painter = imageCard,
                contentDescription = null
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(textOnCard).uppercase(),
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SimpleComposablePreview() {
    HomeScreen(onEvents = {})
}