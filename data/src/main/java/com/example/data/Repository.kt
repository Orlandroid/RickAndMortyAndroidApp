package com.example.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.api.RickAndMortyService
import com.example.data.pagination.CharactersPagingSource
import com.example.data.pagination.EpisodesPagingSource
import com.example.domain.models.local.SearchCharacter
import com.example.domain.models.remote.character.Character
import com.example.domain.models.remote.character.CharacterResponse
import com.example.domain.models.remote.episode.Episode
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(private val rickAndMortyService: RickAndMortyService) {


    companion object {
        const val NETWORK_PAGE_SIZE = 20
        const val PRE_FETCH_DISTANCE = 5
    }

    suspend fun getCharacters(page: Int): CharacterResponse =
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

    fun getEpisodesPagingSource(): Flow<PagingData<Episode>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = PRE_FETCH_DISTANCE
            ),
            pagingSourceFactory = {
                EpisodesPagingSource(service = rickAndMortyService)
            }
        ).flow
    }

    fun getCharactersPagingSource(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = PRE_FETCH_DISTANCE
            ),
            pagingSourceFactory = {
                CharactersPagingSource(service = rickAndMortyService)
            }
        ).flow
    }

}