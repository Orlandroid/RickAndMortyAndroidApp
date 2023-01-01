package com.example.paggingexample.data.models.episode

data class EpisodeResponse(
    val info: Info,
    val results: List<Episode>
)