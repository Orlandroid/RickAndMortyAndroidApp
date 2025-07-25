package com.rickandmortyorlando.orlando.features.characters_detail

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.domain.models.characters.Character
import com.example.domain.models.location.Location
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.app_navigation.AppNavigationRoutes
import com.rickandmortyorlando.orlando.components.CharacterCard
import com.rickandmortyorlando.orlando.components.ErrorScreen
import com.rickandmortyorlando.orlando.components.ToolbarConfiguration
import com.rickandmortyorlando.orlando.components.skeletons.CharacterDetailSkeleton
import com.rickandmortyorlando.orlando.features.base.BaseComposeScreen
import com.rickandmortyorlando.orlando.state.BaseViewState
import com.rickandmortyorlando.orlando.utils.getColorStatusResource
import kotlinx.coroutines.flow.collectLatest


@Composable
fun CharacterDetailRoute(
    navController: NavController,
    idCharacter: Int
) {
    val viewModel: CharacterDetailViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        viewModel.getCharacterDetailInfo(idCharacter)
        viewModel.effects.collectLatest {
            when (it) {
                is CharacterDetailEffects.NavigateToManyEpisodesScreen -> {
                    navController.navigate(AppNavigationRoutes.ManyEpisodesRoute(it.idsOfEpisodes))
                }
            }
        }
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    CharacterDetailScreen(
        viewState = state,
        clickOnCharacter = { id ->
            navController.navigate(
                AppNavigationRoutes.CharactersDetailRoute(id = id)
            )
        },
        clickOnNumberOfEpisodes = {
            viewModel.handleEvent(CharacterDetailEvents.OnClickOnNumberOfEpisodes)
        },
        onBack = { navController.navigateUp() }
    )
}

@Composable
fun CharacterDetailScreen(
    viewState: BaseViewState<CharacterDetailUiState>,
    clickOnCharacter: (Int) -> Unit,
    clickOnNumberOfEpisodes: () -> Unit,
    onBack: () -> Unit
) {
    when (viewState) {
        is BaseViewState.Loading -> {
            BaseComposeScreen(
                toolbarConfiguration = ToolbarConfiguration(
                    title = stringResource(R.string.characters),
                    clickOnBackButton = onBack
                )
            ) {
                CharacterDetailSkeleton()
            }
        }

        is BaseViewState.Error -> {
            ErrorScreen()
        }

        is BaseViewState.Content -> {
            BaseComposeScreen(
                toolbarConfiguration = ToolbarConfiguration(
                    title = viewState.result.characterDetail?.name.orEmpty(),
                    clickOnBackButton = onBack
                )
            ) {
                CharacterDetailScreenContent(
                    uiState = viewState.result,
                    clickOnCharacter = clickOnCharacter,
                    clickOnNumberOfEpisodes = clickOnNumberOfEpisodes
                )
            }
        }
    }
}


@Composable
private fun CharacterDetailScreenContent(
    uiState: CharacterDetailUiState,
    clickOnCharacter: (Int) -> Unit,
    clickOnNumberOfEpisodes: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(32.dp))
        SubcomposeAsyncImage(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = colorResource(getColorStatusResource(uiState.characterDetail?.status.orEmpty())),
                    shape = CircleShape
                ),
            model = uiState.characterDetail?.image,
            contentDescription = "ImageStaff",
            loading = { CircularProgressIndicator(Modifier.padding(16.dp)) }
        )
        Spacer(Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        ) {
            uiState.characterDetail?.let {
                CharacterDetail(
                    character = uiState.characterDetail,
                    statusColor = colorResource(getColorStatusResource(uiState.characterDetail.status)),
                    clickOnNumberOfEpisodes = { clickOnNumberOfEpisodes() }
                )
            }
            Spacer(Modifier.height(32.dp))
            Text(
                text = stringResource(R.string.last_seen_location),
                fontWeight = FontWeight.Bold
            )
            uiState.location?.let { LocationDetails(location = it) }
            Spacer(Modifier.height(32.dp))
            Text(
                text = stringResource(R.string.last_seen_location_residents),
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(16.dp))
            uiState.characterOfThisLocation?.let { characters ->
                LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(characters) { character ->
                        CharacterCard(
                            character = character,
                            onCharacterClick = {
                                if (uiState.characterDetail?.id != character.id) {
                                    clickOnCharacter(character.id)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CharacterDetail(
    character: Character,
    statusColor: Color,
    clickOnNumberOfEpisodes: () -> Unit
) {
    Row {
        Text(text = stringResource(R.string.status), fontWeight = FontWeight.Bold)
        Spacer(Modifier.width(8.dp))
        Canvas(
            modifier = Modifier.size(14.dp),
            onDraw = {
                drawCircle(color = statusColor)
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
    Spacer(Modifier.height(16.dp))
    Row(Modifier.clickable { clickOnNumberOfEpisodes.invoke() }) {
        Text(
            modifier = Modifier.clickable { clickOnNumberOfEpisodes.invoke() },
            text = stringResource(R.string.number_of_episodes),
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.width(8.dp))
        Text(character.episode.size.toString())
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
        Text(text = location.residents.size.toString())
    }
}

@Composable
@Preview(showBackground = true)
private fun CharacterDetailOnContent(modifier: Modifier = Modifier) {
    CharacterDetailScreenContent(
        uiState = CharacterDetailUiState(
            location = Location.mockLocation(),
            characterDetail = Character.mockCharacter(),
            characterOfThisLocation = Character.getCharacters(4),
            idsOfEpisodes = ""
        ),
        clickOnCharacter = { id -> },
        clickOnNumberOfEpisodes = {}
    )
}

