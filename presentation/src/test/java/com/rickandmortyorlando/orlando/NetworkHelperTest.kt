package com.rickandmortyorlando.orlando

import com.rickandmortyorlando.orlando.features.main.NetworkHelper
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import com.google.common.truth.Truth.assertThat

class NetworkHelperTest {

    private lateinit var networkHelper: NetworkHelper

    @Before
    fun setUp() {
        networkHelper = mockk<NetworkHelper>()
    }

    @Test
    fun isConnectedToNetwork() {
        every { networkHelper.isNetworkConnected() } returns false
        assertThat(networkHelper.isNetworkConnected()).isFalse()
    }

    @Test
    fun isConnectedToNetwork_true_trueReturned() {
        every { networkHelper.isNetworkConnected() } returns true
        assertThat(networkHelper.isNetworkConnected()).isTrue()
    }

}