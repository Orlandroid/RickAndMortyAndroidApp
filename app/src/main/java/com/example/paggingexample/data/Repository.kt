package com.example.paggingexample.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.paggingexample.data.api.RickAndMortyService
import com.example.paggingexample.data.models.character.CharacterResponse
import com.example.paggingexample.data.models.episode.Episode
import com.example.paggingexample.data.pagination.EpisodesPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(private val rickAndMortyService: RickAndMortyService) {

    companion object {
        const val NETWORK_PAGE_SIZE = 20
        const val PRE_FETCH_DISTANCE = 5
    }


    suspend fun getCharacters(page: String): CharacterResponse =
        rickAndMortyService.getCharacters(page)

    suspend fun getCharacter(id: String) = rickAndMortyService.getCharacter(id)

    suspend fun getSingleLocation(id: Int) = rickAndMortyService.getSingleLocation(id)

    suspend fun searchCharacter(
        name: String,
        status: String,
        species: String,
        gender: String,
        page: String
    ) = rickAndMortyService.searchCharacter(name, status, species, gender, page)

    fun getEpisodes(): Flow<PagingData<Episode>> {
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


}