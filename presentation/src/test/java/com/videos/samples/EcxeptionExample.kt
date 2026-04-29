package com.videos.samples

import com.videos.data.UserFromLocalRepository
import com.videos.data.UserRemoteRepository
import com.videos.domain.UserManager
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import com.videos.domain.UserResult

class ExceptionSample {

    private val localRepository: UserFromLocalRepository = mockk()
    private val remoteRepository: UserRemoteRepository = mockk()
    private val userManager =
        UserManager(localRepository = localRepository, remoteRepository = remoteRepository)


    @Test
    fun `returns Success when remote repository works`() = runTest {
        val fakeUser = "FakeUser"
        coEvery { remoteRepository.getUserName() } returns fakeUser

        val result = userManager.getUser(true)

        coVerify { remoteRepository.getUserName() }
        coVerify(exactly = 0) { localRepository.getUserName() }

        assertThat(result).isEqualTo(UserResult.Success(fakeUser))
    }

    @Test
    fun `returns Error when remote repository throws exception`() = runTest {
        val errorMessage = "Network time"
        coEvery { remoteRepository.getUserName() } throws  Exception(errorMessage)

        val result = userManager.getUser(true)

        coVerify { remoteRepository.getUserName() }
        coVerify(exactly = 0) { localRepository.getUserName() }

        assertThat(result).isEqualTo(UserResult.Error(errorMessage))
    }
}
