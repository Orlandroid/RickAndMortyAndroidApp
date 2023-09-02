package com.example.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.api.RickAndMortyService
import com.example.domain.models.remote.episode.Episode


private const val STARTING_PAGE_INDEX = 1

class EpisodesPagingSource(
    private val service: RickAndMortyService
) : PagingSource<Int, Episode>() {

    companion object {
        private const val START_PAGE = 1
    }

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Episode> {
        return try {
            val currentPage = params.key ?: 1
            val data = service.getEpisodes(currentPage).results
            LoadResult.Page(
                data = data,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = if (data.isEmpty()) null else currentPage.plus(1)
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
