package com.example.data.api

import com.example.data.model.location.LocationsResponse
import com.example.data.model.character.CharacterData
import com.example.data.model.character.CharacterResponse
import com.example.data.model.episode.EpisodeData
import com.example.data.model.episode.EpisodeResponse
import com.example.data.model.location.SingleLocation
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface RickAndMortyService {

    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): CharacterResponse

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: String): CharacterData

    @GET
    suspend fun getManyCharacters(
        @Url url: String
    ): List<CharacterData>


    @GET("location/{id}")
    suspend fun getSingleLocation(@Path("id") id: Int): SingleLocation

    @GET("location")
    suspend fun getLocations(@Query("page") page: Int): LocationsResponse

    @GET("character")
    suspend fun searchCharacter(
        @Query("name") name: String,
        @Query("status") status: String,
        @Query("species") species: String,
        @Query("gender") gender: String,
        @Query("page") page: String,
        @Query("type") type: String,
    ): CharacterResponse

    @GET("episode")
    suspend fun getEpisodes(
        @Query("page") page: Int
    ): EpisodeResponse


    @GET
    suspend fun getManyEpisodes(
        @Url url: String
    ): List<EpisodeData>


}