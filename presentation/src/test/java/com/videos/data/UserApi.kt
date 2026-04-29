package com.videos.data

import com.videos.domain.ApiResult

interface UserApi {
    suspend fun fetchUser(userId: String): ApiResult<String>
}