package com.rickandmortyorlando.orlando.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.rickandmortyorlando.orlando.R


fun getColorStatusResource(status: String? = "unknown"): Int {
    return when (status) {
        "Alive" -> {
            R.color.alive
        }

        "Dead" -> {
            R.color.dead
        }

        "unknown" -> {
            R.color.unknown
        }

        else -> {
            R.color.unknown
        }
    }
}

@Composable
fun String.getColorStatus(): Color {
    when (this) {
        "Alive" -> {
            return colorResource(R.color.alive)
        }

        "Dead" -> {
            return colorResource(R.color.dead)
        }

        "unknown" -> {
            return colorResource(R.color.unknown)
        }

        else -> {
            return colorResource(R.color.unknown)
        }
    }
}


fun removeCharactersForEpisodesList(episodesList: String): String {
    return episodesList.replace("[", "").replace("]", "").replace(" ", "")
}

