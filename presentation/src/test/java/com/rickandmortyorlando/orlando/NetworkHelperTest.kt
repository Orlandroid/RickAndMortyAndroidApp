package com.rickandmortyorlando.orlando

import com.rickandmortyorlando.orlando.features.main.NetworkHelper
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class NetworkHelperTest {

    private lateinit var networkHelper: NetworkHelper

    @Before
    fun setUp() {
        networkHelper = Mockito.mock(NetworkHelper::class.java)
    }

    @Test
    fun isConnectedToNetwork() {
        Mockito.`when`(networkHelper.isNetworkConnected()).thenReturn(false)
        assertThat(networkHelper.isNetworkConnected(), `is`(false))
    }

    @Test
    fun isConnectedToNetwork_true_trueReturned() {
        Mockito.`when`(networkHelper.isNetworkConnected()).thenReturn(true)
        assertThat(networkHelper.isNetworkConnected(), `is`(true))
    }

}