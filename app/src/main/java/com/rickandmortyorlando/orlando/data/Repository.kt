package com.rickandmortyorlando.orlando.data

import com.rickandmortyorlando.orlando.data.api.RickAndMortyService
import com.rickandmortyorlando.orlando.data.models.local.SearchCharacter
import com.rickandmortyorlando.orlando.data.models.remote.character.Character
import com.rickandmortyorlando.orlando.data.models.remote.character.CharacterResponse
import com.rickandmortyorlando.orlando.data.models.remote.episode.Episode
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


    suspend fun getManyEpisodes(ids: String): List<Episode> {
        val baseUrl = "https://rickandmortyapi.com/api/episode/$ids"
        return rickAndMortyService.getManyEpisodes(url = baseUrl)
    }

    suspend fun getManyCharacters(ids: String): List<Character> {
        val baseUrl = "https://rickandmortyapi.com/api/character/$ids"
        return rickAndMortyService.getManyCharacters(url = baseUrl)
    }


    suspend fun getLocations(page: String) = rickAndMortyService.getLocations(page)

}