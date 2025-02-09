package com.rickandmortyorlando.orlando.features.episode_detail


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage

@Composable
fun EpisodeDetailScreen() {
    Column(Modifier.fillMaxWidth()) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .size(100.dp),
            model = "character.image",
            contentDescription = "ImageStaff",
            loading = { CircularProgressIndicator() },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EpisodeDetailScreenPreview() {
    EpisodeDetailScreen()
}
