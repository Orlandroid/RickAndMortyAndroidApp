package com.example.paggingexample.data

import com.example.paggingexample.data.api.RickAndMortyService
import com.example.paggingexample.data.models.character.CharacterResponse
import javax.inject.Inject

class Repository @Inject constructor(private val rickAndMortyService: RickAndMortyService) {

    suspend fun getCharacters(page: String): CharacterResponse = rickAndMortyService.getCharacters(page)

    suspend fun getCharacter(id: String) = rickAndMortyService.getCharacter(id)

    suspend fun getSingleLocation(id: Int) = rickAndMortyService.getSingleLocation(id)

}