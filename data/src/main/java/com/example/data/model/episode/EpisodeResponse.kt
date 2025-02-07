package com.example.data.model.episode

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