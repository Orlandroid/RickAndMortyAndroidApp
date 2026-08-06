package com.example.domain.paginator

interface Paginator<Key : Any, Item : Any> {
    suspend fun load(key: Key?, onTotal: (Int) -> Unit = {}): PageResult<Key, Item>
}

sealed class PageResult<out Key : Any, out Item : Any> {
    data class Success<Key : Any, Item : Any>(
        val items: List<Item>,
        val prevKey: Key?,
        val nextKey: Key?
    ) : PageResult<Key, Item>()

    data class Error(val throwable: Throwable) : PageResult<Nothing, Nothing>()
}