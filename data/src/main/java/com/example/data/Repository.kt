package com.example.data

import com.example.data.api.RickAndMortyService
import com.example.data.model.character.CharacterData
import com.example.data.model.episode.EpisodeData
import javax.inject.Inject

class Repository @Inject constructor(private val rickAndMortyService: RickAndMortyService) {


    companion object {
        const val NETWORK_PAGE_SIZE = 20
        const val PRE_FETCH_DISTANCE = 5
    }


    suspend fun getCharacter(id: String) = rickAndMortyService.getCharacter(id)

    suspend fun getSingleLocation(id: Int) = rickAndMortyService.getSingleLocation(id)


    suspend fun getManyEpisodes(ids: String): List<EpisodeData> {
        val baseUrl = "https://rickandmortyapi.com/api/episode/$ids"
        return rickAndMortyService.getManyEpisodes(url = baseUrl)
    }

    suspend fun getManyCharacters(ids: String): List<CharacterData> {
        val baseUrl = "https://rickandmortyapi.com/api/character/$ids"
        return rickAndMortyService.getManyCharacters(url = baseUrl)
    }

}