package com.example.data.api

import com.example.domain.models.remote.location.LocationsResponse
import com.example.domain.models.remote.character.Character
import com.example.domain.models.remote.character.CharacterResponse
import com.example.domain.models.remote.episode.EpisodeResponse
import com.example.domain.models.remote.location.SingleLocation
import com.example.domain.models.remote.episode.Episode
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface RickAndMortyService {

    @GET("character")
    suspend fun getCharacters(@Query("page") page: String): CharacterResponse

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: String): Character

    @GET
    suspend fun getManyCharacters(
        @Url url: String
    ): List<Character>


    @GET("location/{id}")
    suspend fun getSingleLocation(@Path("id") id: Int): SingleLocation

    @GET("location")
    suspend fun getLocations(@Query("page") page: String): LocationsResponse

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


    @GET
    suspend fun getManyEpisodes(
        @Url url: String
    ): List<Episode>


}