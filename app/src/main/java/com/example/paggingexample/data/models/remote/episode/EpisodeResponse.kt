package com.example.paggingexample.data.models.remote.location.episode

import com.example.paggingexample.data.models.remote.episode.Info

data class EpisodeResponse(
    val info: Info,
    val results: List<Episode>
)