package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.data.api.RickAndMortyService
import com.example.data.pagination.CharactersPagingSource
import com.example.data.pagination.getPagingConfig
import com.example.domain.models.characters.Character
import com.example.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val api: RickAndMortyService) : CharacterRepository {

    override fun getCharacters(): Flow<PagingData<Character>> {
        return Pager(
            config = getPagingConfig(),
            pagingSourceFactory = {
                CharactersPagingSource(service = api)
            }
        ).flow
    }

}