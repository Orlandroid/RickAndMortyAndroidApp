package com.example.domain.models.remote.episode

data class EpisodeResponse(
    val info: Info,
    val results: List<Episode>
)