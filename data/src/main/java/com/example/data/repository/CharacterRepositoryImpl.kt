package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.data.api.RickAndMortyService
import com.example.data.model.character.toCharacter
import com.example.data.pagination.CharactersPagingSource
import com.example.data.pagination.CharactersSearchPagingSource
import com.example.data.pagination.getPagingConfig
import com.example.domain.models.characters.Character
import com.example.domain.models.characters.SearchCharacter
import com.example.domain.repository.CharacterRepository
import com.example.domain.state.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: RickAndMortyService
) :
    CharacterRepository {

    private lateinit var charactersPagingSource: CharactersPagingSource
    private lateinit var charactersSearchPagingSource: CharactersSearchPagingSource

    override fun getCharacters(): Flow<PagingData<Character>> {
        return Pager(
            config = getPagingConfig(),
            pagingSourceFactory = {
                charactersPagingSource = CharactersPagingSource(service = api)
                charactersPagingSource
            }
        ).flow
    }

    override suspend fun getCharacter(idCharacter: String): ApiResult<Character> {
        return runCatching {
            api.getCharacter(idCharacter).toCharacter()
        }.fold(
            onSuccess = { character -> ApiResult.Success(character) },
            onFailure = { throwable -> ApiResult.Error(msg = throwable.message.orEmpty()) }
        )
    }

    override suspend fun getManyCharacters(idsCharacters: String): List<Character> {
        val url = "https://rickandmortyapi.com/api/character/$idsCharacters"
        return if (idsCharacters.contains(",")) {
            api.getManyCharacters(url).map { it.toCharacter() }
        } else {
            listOf(api.getCharacter(id = idsCharacters).toCharacter())
        }
    }

    override fun searchCharacter(searchCharacter: SearchCharacter): Flow<PagingData<Character>> {
        return Pager(
            config = getPagingConfig(),
            pagingSourceFactory = {
                charactersSearchPagingSource = CharactersSearchPagingSource(
                    service = api,
                    search = searchCharacter
                )
                charactersSearchPagingSource
            }
        ).flow
    }

}