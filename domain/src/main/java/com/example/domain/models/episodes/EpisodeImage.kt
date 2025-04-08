package com.example.domain.models.episodes

data class EpisodeImage(
    val id: Int,
    val url: String,
    val name: String,
    val season: Int,
    val number: Int,
    val type: String,
    val average: Double?,
    val imageUrl: String,
    val summary: String
)