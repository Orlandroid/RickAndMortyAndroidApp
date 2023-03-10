package com.rickandmortyorlando.paggingexample.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rickandmortyorlando.paggingexample.data.api.RickAndMortyService
import com.rickandmortyorlando.paggingexample.data.models.remote.episode.Episode

class EpisodesPagingSource(
    val service: RickAndMortyService
) : PagingSource<Int, Episode>() {

    companion object {
        private const val START_PAGE = 1
    }

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Episode> {
        return try {
            val nextPageNumber = params.key ?: START_PAGE
            val response = service.getEpisodes(nextPageNumber)
            val nextPage = response.info.next.split("=")[1].toInt()
            LoadResult.Page(
                data = response.results,
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Episode>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}