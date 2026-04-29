package com.videos.data

import com.videos.domain.UserRepository

class UserFromLocalRepository() : UserRepository {

    override suspend fun getUserName(): String {
        return "UserLocal"
    }
}