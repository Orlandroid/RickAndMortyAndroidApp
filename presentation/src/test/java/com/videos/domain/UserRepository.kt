package com.videos.domain

interface UserRepository {
    suspend fun getUserName(): String
}