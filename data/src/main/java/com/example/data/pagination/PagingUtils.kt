package com.example.data.pagination

import androidx.paging.PagingConfig

const val NETWORK_PAGE_SIZE = 20
const val PRE_FETCH_DISTANCE = 5

fun getPagingConfig() = PagingConfig(
    pageSize = NETWORK_PAGE_SIZE,
    enablePlaceholders = false,
    prefetchDistance = PRE_FETCH_DISTANCE
)

