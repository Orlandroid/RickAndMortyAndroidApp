package com.example.paggingexample.data

import com.example.paggingexample.data.api.RickAndMortyService
import com.example.paggingexample.data.models.character.CharacterResponse
import retrofit2.http.Query
import javax.inject.Inject

class Repository @Inject constructor(private val rickAndMortyService: RickAndMortyService) {

    suspend fun getCharacters(page: String): CharacterResponse =
        rickAndMortyService.getCharacters(page)

    suspend fun getCharacter(id: String) = rickAndMortyService.getCharacter(id)

    suspend fun getSingleLocation(id: Int) = rickAndMortyService.getSingleLocation(id)

    suspend fun searchCharacter(name: String, status: String, species: String, gender: String) =
        rickAndMortyService.searchCharacter(name, status, species, gender)

}