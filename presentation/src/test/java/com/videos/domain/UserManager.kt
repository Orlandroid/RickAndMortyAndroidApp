package com.videos.domain

import com.videos.data.UserFromLocalRepository
import com.videos.data.UserRemoteRepository


sealed class UserResult {
    data class Success(val name: String) : UserResult()
    data class Error(val message: String) : UserResult()
}

class UserManager(
    private val localRepository: UserFromLocalRepository,
    private val remoteRepository: UserRemoteRepository
) {
    suspend fun getUser(fromRemote: Boolean): UserResult {
        return try {
            val name = if (fromRemote) {
                remoteRepository.getUserName()
            } else {
                localRepository.getUserName()
            }
            UserResult.Success(name)
        } catch (e: Exception) {
            UserResult.Error(e.message ?: "Unknown error")
        }
    }
}