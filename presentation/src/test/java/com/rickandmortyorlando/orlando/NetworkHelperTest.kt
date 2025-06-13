package com.rickandmortyorlando.orlando

import com.rickandmortyorlando.orlando.features.main.NetworkHelper
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class NetworkHelperTest {

    private lateinit var networkHelper: NetworkHelper

    @Before
    fun setUp() {
        networkHelper = mockk<NetworkHelper>()
    }

    @Test
    fun isConnectedToNetwork() {
        every { networkHelper.isNetworkConnected() } returns false
        assert(networkHelper.isNetworkConnected().not())
    }

    @Test
    fun isConnectedToNetwork_true_trueReturned() {
        every { networkHelper.isNetworkConnected() } returns true
        assert(networkHelper.isNetworkConnected())
    }

}