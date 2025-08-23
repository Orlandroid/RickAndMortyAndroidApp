package com.videos

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Test


interface UserRepository {
    suspend fun getUserName(): String
}


class UserFromLocalRepository() : UserRepository {

    override suspend fun getUserName(): String {
        return "UserLocal"
    }
}

class UserRemoteRepository() : UserRepository {

    override suspend fun getUserName(): String {
        return "UserFromRemote"
    }
}

class UserManager(
    private val localRepository: UserFromLocalRepository,
    private val remoteRepository: UserRemoteRepository
) {
    suspend fun getUser(fromRemote: Boolean): String {
        return if (fromRemote) {
            remoteRepository.getUserName()
        } else {
            localRepository.getUserName()
        }
    }
}


sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String) : ApiResult<Nothing>()
}

interface UserApi {
    suspend fun fetchUser(userId: String): ApiResult<String>
}

class FirstExample {

    @Test
    fun checkUser() = runTest {
        val expectedUser = "MockUser"
        val userRemoteRepository = mockk<UserRemoteRepository>()
        coEvery { userRemoteRepository.getUserName() } returns expectedUser
        val user = userRemoteRepository.getUserName()
        assertThat(user).isEqualTo(expectedUser)
    }

}

class SecondExampleCoVerify {

    @Test
    fun calls_remote_repository_when_fromRemote_is_True() = runTest {
        val localRepository: UserFromLocalRepository = mockk(relaxed = true)
        val remoteRepository: UserRemoteRepository = mockk(relaxed = true)
        val userManager = UserManager(localRepository, remoteRepository)
        userManager.getUser(true)
        coVerify(exactly = 1) { remoteRepository.getUserName() }
        coVerify(exactly = 0) { localRepository.getUserName() }
    }

    @Test
    fun calls_local_repository_when_fromRemote_is_False() = runTest {
        val localRepository: UserFromLocalRepository = mockk(relaxed = true)
        val remoteRepository: UserRemoteRepository = mockk(relaxed = true)
        val userManager = UserManager(localRepository, remoteRepository)
        userManager.getUser(false)
        coVerify(exactly = 0) { remoteRepository.getUserName() }
        coVerify(exactly = 1) { localRepository.getUserName() }
    }
}