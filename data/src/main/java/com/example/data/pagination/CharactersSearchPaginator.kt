package com.example.data.pagination

import com.example.data.api.RickAndMortyService
import com.example.data.model.character.toCharacter
import com.example.domain.models.characters.SearchCharacter
import com.example.domain.paginator.PageResult
import com.example.domain.paginator.Paginator
import com.example.domain.models.characters.Character
import retrofit2.HttpException

class CharactersSearchPaginator(
    private val service: RickAndMortyService,
    private val search: SearchCharacter
) : Paginator<Int, Character> {

    override suspend fun load(key: Int?, onTotal: (Int) -> Unit): PageResult<Int, Character> {
        val currentPage = key ?: 1
        return try {
            val data = service.searchCharacter(
                name = search.name, status = search.status,
                species = search.species, gender = search.gender,
                type = search.type, page = currentPage.toString()
            )
            if (currentPage == 1) onTotal(data.info.count)
            PageResult.Success(
                items = data.results.map { it.toCharacter() },
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (data.info.next == null) null else currentPage + 1
            )
        } catch (e: Exception) {
            val err = (e as? HttpException)?.response()?.errorBody()
                ?.byteStream()?.bufferedReader()?.use { it.readText() }
            PageResult.Error(Throwable(err ?: e.message, e))
        }
    }
}