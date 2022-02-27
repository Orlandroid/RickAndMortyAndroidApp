package com.example.paggingexample.data.api

import com.example.paggingexample.data.models.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyService {

    @GET("character")
    suspend fun getCharacters(@Query("page") page: String):CharacterResponse
}