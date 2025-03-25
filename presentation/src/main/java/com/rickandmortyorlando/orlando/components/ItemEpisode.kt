package com.rickandmortyorlando.orlando.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.models.episodes.Episode
import com.rickandmortyorlando.orlando.R


@Composable
fun ItemEpisode(
    episode: Episode,
    clickOnItem: (episodeId: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { clickOnItem(episode.id) }) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = episode.episode,
                    fontWeight = FontWeight.Bold
                )
                Text(modifier = Modifier.fillMaxWidth(), text = episode.name)
                Text(modifier = Modifier.fillMaxWidth(), text = episode.airDate)
            }
            Icon(
                modifier = Modifier.padding(end = 16.dp),
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null
            )
        }
        HorizontalDivider(
            modifier = Modifier
                .padding(top = 8.dp)
                .background(colorResource(R.color.gris))
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun EpisodeItemPreview(modifier: Modifier = Modifier) {
    ItemEpisode(
        episode = Episode(
            airDate = stringResource(R.string.december_2_2013),
            characters = emptyList(),
            created = "",
            episode = stringResource(R.string.s01e01),
            id = 1,
            name = stringResource(R.string.pilot),
            url = ""
        ),
        clickOnItem = {})
}