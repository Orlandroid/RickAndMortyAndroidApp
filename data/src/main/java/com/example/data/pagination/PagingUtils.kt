package com.example.data.pagination

import androidx.paging.PagingConfig
import com.example.data.Repository

fun getPagingConfig() = PagingConfig(
    pageSize = Repository.NETWORK_PAGE_SIZE,
    enablePlaceholders = false,
    prefetchDistance = Repository.PRE_FETCH_DISTANCE
)