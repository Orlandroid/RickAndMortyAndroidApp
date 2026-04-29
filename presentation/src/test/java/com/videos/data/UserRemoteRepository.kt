package com.videos.data

import com.videos.domain.UserRepository

class UserRemoteRepository() : UserRepository {

    override suspend fun getUserName(): String {
        return "UserFromRemote"
    }
}