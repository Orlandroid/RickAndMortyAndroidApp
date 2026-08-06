package com.example.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.domain.paginator.PageResult
import com.example.domain.paginator.Paginator


class PagingSourceAdapter<Item : Any>(
    private val paginator: Paginator<Int, Item>
) : PagingSource<Int, Item>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        return when (val result = paginator.load(params.key)) {
            is PageResult.Success -> LoadResult.Page(result.items, result.prevKey, result.nextKey)
            is PageResult.Error -> LoadResult.Error(result.throwable)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Item>): Int? =
        state.anchorPosition?.let { pos ->
            val page = state.closestPageToPosition(pos)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
}