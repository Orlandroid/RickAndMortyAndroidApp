package com.example.paggingexample.data.models.remote.episode

data class EpisodeResponse(
    val info: Info,
    val results: List<Episode>
)