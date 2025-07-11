package com.rickandmortyorlando.orlando.features.episode_detail


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.domain.models.characters.Character
import com.example.domain.models.episodes.Episode
import com.example.domain.models.episodes.EpisodeImage
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.app_navigation.AppNavigationRoutes
import com.rickandmortyorlando.orlando.components.ErrorScreen
import com.rickandmortyorlando.orlando.components.ItemCharacter
import com.rickandmortyorlando.orlando.components.ToolbarConfiguration
import com.rickandmortyorlando.orlando.components.skeletons.EpisodeDetailSkeleton
import com.rickandmortyorlando.orlando.features.base.BaseComposeScreen
import com.rickandmortyorlando.orlando.features.extensions.openYoutubeApp
import com.rickandmortyorlando.orlando.state.BaseViewState


@Composable
fun EpisodesDetailRoute(navController: NavController, episodesId: String) {
    val context = LocalContext.current
    val viewModel: EpisodeDetailViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        viewModel.getEpisodeDetail(episodesId)
    }
    val state = viewModel.state.collectAsStateWithLifecycle()
    EpisodeDetailScreen(
        viewState = state.value,
        clickOnCharacter = { characterId ->
            navController.navigate(
                AppNavigationRoutes.CharactersDetailRoute(
                    id = characterId
                )
            )
        },
        clickOnWatch = { episodeName ->
            context.openYoutubeApp(episodeName)
        },
        onBackPress = {
            navController.navigateUp()
        }
    )
}

@Composable
private fun EpisodeDetailScreen(
    viewState: BaseViewState<EpisodeDetailUiState>,
    clickOnCharacter: (characterId: Int) -> Unit,
    clickOnWatch: (episodeQuery: String) -> Unit,
    onBackPress: () -> Unit
) {
    when (viewState) {
        is BaseViewState.Loading -> {
            BaseComposeScreen(
                toolbarConfiguration = ToolbarConfiguration(
                    title = stringResource(R.string.episode_detail),
                    clickOnBackButton = onBackPress
                )
            ) {
                EpisodeDetailSkeleton()
            }
        }

        is BaseViewState.Error -> {
            ErrorScreen()
        }

        is BaseViewState.Content -> {
            BaseComposeScreen(
                toolbarConfiguration = ToolbarConfiguration(
                    title = viewState.result.episode.name,
                    clickOnBackButton = onBackPress
                )
            ) {
                EpisodeDetailScreenContent(
                    uiState = viewState.result,
                    clickOnCharacter = clickOnCharacter,
                    clickOnWatch = clickOnWatch
                )
            }
        }
    }
}

@Composable
private fun EpisodeDetailScreenContent(
    uiState: EpisodeDetailUiState,
    clickOnCharacter: (characterId: Int) -> Unit,
    clickOnWatch: (episodeQuery: String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(8.dp))
        SubcomposeAsyncImage(
            model = uiState.episodeImage?.imageUrl,
            contentDescription = "EpisodeImage",
            loading = { CircularProgressIndicator(Modifier.padding(16.dp)) }
        )
        Spacer(Modifier.height(32.dp))
        Column(Modifier.padding(start = 32.dp)) {
            Row(Modifier.fillMaxWidth()) {
                Text(
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.episode_name)
                )
                Text(modifier = Modifier.weight(1f), text = uiState.episode.name)
            }
            Spacer(Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth()) {
                Text(
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.episode_number)
                )
                Text(modifier = Modifier.weight(1f), text = uiState.episode.episode)
            }
            Spacer(Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth()) {
                Text(
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.episode_date)
                )
                Text(modifier = Modifier.weight(1f), text = uiState.episode.airDate)
            }
            Spacer(Modifier.height(16.dp))
            Text(
                modifier = Modifier.clickable {
                    clickOnWatch("${uiState.episode.name} ${uiState.episode.episode}")
                },
                text = stringResource(R.string.watch),
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(
            fontSize = 32.sp,
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.characters),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(32.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(
                count = uiState.characters.size
            ) { character ->
                ItemCharacter(
                    character = uiState.characters[character],
                    clickOnItem = clickOnCharacter
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EpisodeDetailScreenPreview() {
    EpisodeDetailScreenContent(
        EpisodeDetailUiState(
            episode = Episode.mockEpisode(),
            characters = Character.getCharacters(9),
            episodeImage = EpisodeImage(
                id = 0,
                url = "",
                name = "",
                season = 1,
                number = 1,
                type = "",
                average = 0.0,
                imageUrl = "",
                summary = ""
            )
        ),
        clickOnCharacter = { id -> },
        clickOnWatch = {}
    )
}
