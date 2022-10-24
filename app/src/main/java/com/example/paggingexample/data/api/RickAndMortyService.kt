package com.example.paggingexample.data.api

import com.example.paggingexample.data.models.Character
import com.example.paggingexample.data.models.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.net.IDN

interface RickAndMortyService {

    @GET("character")
    suspend fun getCharacters(@Query("page") page: String): CharacterResponse

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: String): Character
}