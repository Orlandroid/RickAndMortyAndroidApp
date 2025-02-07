package com.example.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.api.RickAndMortyService
import com.example.data.model.character.toCharacter
import com.example.domain.models.SearchCharacter
import com.example.domain.models.characters.Character
import retrofit2.HttpException

class CharactersSearchPagingSource(
    private val service: RickAndMortyService,
    private val search: SearchCharacter
) : PagingSource<Int, Character>() {

    companion object {
        private const val START_PAGE = 1
    }

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Character> {
        return try {
            val currentPage = params.key ?: START_PAGE
            val data = service.searchCharacter(
                name = search.name,
                status = search.status,
                species = search.species,
                gender = search.gender,
                type = search.type,
                page = currentPage.toString()
            ).results.map { it.toCharacter() }
            LoadResult.Page(
                data = data,
                prevKey = if (currentPage == START_PAGE) null else currentPage - 1,
                nextKey = if (data.isEmpty()) null else currentPage.plus(1)
            )
        } catch (e: Exception) {
            if (e is HttpException) {
                val errorString =
                    e.response()?.errorBody()?.byteStream()?.bufferedReader().use { it?.readText() }
                LoadResult.Error(Throwable(errorString))
            } else {
                LoadResult.Error(e)
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}
