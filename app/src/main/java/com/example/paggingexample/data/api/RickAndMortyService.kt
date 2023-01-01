package com.example.paggingexample.data.api

import com.example.paggingexample.data.models.character.Character
import com.example.paggingexample.data.models.character.CharacterResponse
import com.example.paggingexample.data.models.episode.EpisodeResponse
import com.example.paggingexample.data.models.location.SingleLocation
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyService {

    @GET("character")
    suspend fun getCharacters(@Query("page") page: String): CharacterResponse

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: String): Character

    @GET("location/{id}")
    suspend fun getSingleLocation(@Path("id") id: Int): SingleLocation

    @GET("character")
    suspend fun searchCharacter(
        @Query("name") name: String,
        @Query("status") status: String,
        @Query("species") species: String,
        @Query("gender") gender: String,
        @Query("page") page: String
    ): CharacterResponse

    @GET("episode")
    suspend fun getEpisodes(
        @Query("page") page: Int
    ): EpisodeResponse


}