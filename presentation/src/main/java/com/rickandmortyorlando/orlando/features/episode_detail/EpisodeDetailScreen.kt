package com.rickandmortyorlando.orlando.features.episode_detail


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.models.characters.Character
import com.example.domain.models.episodes.Episode
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.components.ItemCharacter

@Composable
fun EpisodeDetailScreen(
    uiState: EpisodeDetailUiState,
    clickOnCharacter: (characterId: Int) -> Unit,
    clickOnWatch: (episodeQuery: String) -> Unit,
) {
    Column(Modifier.fillMaxWidth()) {
        Spacer(Modifier.height(8.dp))
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            painter = painterResource(R.drawable.morty_and_rick),
            contentDescription = "EpisodeImage"
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
    EpisodeDetailScreen(
        EpisodeDetailUiState(
            episode = Episode.mockEpisode(),
            characters = Character.getCharacters(9),
        ),
        clickOnCharacter = {},
        clickOnWatch = {}
    )
}
