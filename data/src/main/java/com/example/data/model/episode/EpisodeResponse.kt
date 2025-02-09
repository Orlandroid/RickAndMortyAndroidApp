package com.example.data.model.episode

import com.example.data.model.Info
import com.example.domain.models.episodes.Episode

data class EpisodeResponse(
    val info: Info,
    val results: List<EpisodeData>
)

data class EpisodeData(
    val air_date: String,
    val characters: List<String>,
    val created: String,
    val episode: String,
    val id: Int,
    val name: String,
    val url: String
)

fun EpisodeData.toEpisode() =
    Episode(
        airDate = air_date,
        characters = characters,
        created = created,
        episode = episode,
        id = id,
        name = name,
        url = url
    )