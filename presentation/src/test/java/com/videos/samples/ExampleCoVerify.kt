package com.videos.samples

import com.videos.data.UserFromLocalRepository
import com.videos.data.UserRemoteRepository
import com.videos.domain.UserManager
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ExampleCoVerify {

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