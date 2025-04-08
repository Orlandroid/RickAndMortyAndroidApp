package com.example.data.api

import com.example.data.model.character.EpisodeImageResponse
import retrofit2.http.GET

interface RickAndMortyEpisodesImages {

    @GET("shows/216/episodes")
    suspend fun getImagesEpisodes(): List<EpisodeImageResponse>
}