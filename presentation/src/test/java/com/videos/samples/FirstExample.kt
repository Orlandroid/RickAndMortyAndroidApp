package com.videos.samples

import com.google.common.truth.Truth.assertThat
import com.videos.data.UserRemoteRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

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