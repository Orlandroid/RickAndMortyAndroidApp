package com.example.paggingexample.data

import com.example.paggingexample.data.api.RickAndMortyService
import com.example.paggingexample.data.models.Character
import com.example.paggingexample.data.models.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject

class Repository @Inject constructor(private val rickAndMortyService: RickAndMortyService) {

    suspend fun getCharacters(page: String): CharacterResponse {
        return rickAndMortyService.getCharacters(page)
    }

    suspend fun getCharacter(id: String) = rickAndMortyService.getCharacter(id)

}