package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.data.api.RickAndMortyService
import com.example.data.model.character.toCharacter
import com.example.data.pagination.CharactersPagingSource
import com.example.data.pagination.getPagingConfig
import com.example.domain.models.characters.Character
import com.example.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val api: RickAndMortyService) :
    CharacterRepository {

    private lateinit var charactersPagingSource: CharactersPagingSource

    override fun getCharacters(): Flow<PagingData<Character>> {
        return Pager(
            config = getPagingConfig(),
            pagingSourceFactory = {
                charactersPagingSource = CharactersPagingSource(service = api)
                charactersPagingSource
            }
        ).flow
    }

    override suspend fun getCharacter(idCharacter: String): Character {
        return api.getCharacter(idCharacter).toCharacter()
    }

    override suspend fun getManyCharacters(idsCharacters:String): List<Character> {
        val url = "https://rickandmortyapi.com/api/character/$idsCharacters"
        return api.getManyCharacters(url).map { it.toCharacter() }
    }

}