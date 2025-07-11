package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.models.characters.Character
import com.example.domain.models.characters.SearchCharacter
import com.example.domain.state.ApiResult
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getCharacters(): Flow<PagingData<Character>>

    suspend fun getCharacter(idCharacter: String): ApiResult<Character>

    suspend fun getManyCharacters(idsCharacters: String): ApiResult<List<Character>>

    fun searchCharacter(searchCharacter: SearchCharacter): Flow<PagingData<Character>>
}