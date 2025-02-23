package com.example.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.api.RickAndMortyService
import com.example.data.model.episode.toEpisode
import com.example.domain.models.episodes.Episode
import kotlinx.coroutines.delay
import retrofit2.HttpException
import kotlin.time.Duration.Companion.seconds


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
            val currentPage = params.key ?: START_PAGE
            val data = service.getEpisodes(currentPage).results.map { it.toEpisode() }
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

    override fun getRefreshKey(state: PagingState<Int, Episode>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
