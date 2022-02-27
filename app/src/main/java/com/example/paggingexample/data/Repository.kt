package com.example.paggingexample.data

import com.example.paggingexample.data.api.RickAndMortyService
import com.example.paggingexample.data.models.CharacterResponse
import javax.inject.Inject

class Repository @Inject constructor(private val rickAndMortyService: RickAndMortyService) {

    suspend fun getCharacters(page: String):CharacterResponse {
        return rickAndMortyService.getCharacters(page)
    }

}