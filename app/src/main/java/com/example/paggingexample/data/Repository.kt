package com.example.paggingexample.data

import com.example.paggingexample.data.api.RickAndMortyService
import com.example.paggingexample.data.models.local.SearchCharacter
import com.example.paggingexample.data.models.remote.location.character.CharacterResponse
import javax.inject.Inject

class Repository @Inject constructor(private val rickAndMortyService: RickAndMortyService) {


    suspend fun getCharacters(page: String): CharacterResponse =
        rickAndMortyService.getCharacters(page)

    suspend fun getCharacter(id: String) = rickAndMortyService.getCharacter(id)

    suspend fun getSingleLocation(id: Int) = rickAndMortyService.getSingleLocation(id)

    suspend fun searchCharacter(searchCharacter: SearchCharacter, page: String) =
        rickAndMortyService.searchCharacter(
            searchCharacter.name,
            searchCharacter.status,
            searchCharacter.species,
            searchCharacter.gender,
            page
        )

    suspend fun getEpisodes(page: Int) = rickAndMortyService.getEpisodes(page)

    suspend fun getLocations(page: String) = rickAndMortyService.getLocations(page)

}