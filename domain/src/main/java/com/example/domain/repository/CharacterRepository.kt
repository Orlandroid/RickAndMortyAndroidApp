package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.models.characters.Character
import com.example.domain.models.characters.SearchCharacter
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getCharacters(): Flow<PagingData<Character>>

    suspend fun getCharacter(idCharacter: String): Character

    suspend fun getManyCharacters(idsCharacters: String): List<Character>

    fun searchCharacter(searchCharacter: SearchCharacter): Flow<PagingData<Character>>
}