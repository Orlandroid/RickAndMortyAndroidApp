package com.rickandmortyorlando.orlando.features.home

import app.cash.turbine.test
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        viewModel = HomeViewModel()
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when ClickOnCharacters event is call check that NavigateToCharacters is emit `() =
        runTest {
            val homeEffects = mutableListOf<HomeEffects>()
            val job = backgroundScope.launch(UnconfinedTestDispatcher()) {
                viewModel.effects.toList(homeEffects)
            }
            viewModel.onEvents(HomeEvents.ClickOnCharacters)
            assertTrue(homeEffects.contains(HomeEffects.NavigateToCharacters))
            job.cancel()
        }

    @Test
    fun `when ClickOnEpisodes event is call check that NavigateToCharacters effect is emit`() =
        runTest {
            viewModel.effects.test {
                viewModel.onEvents(HomeEvents.ClickOnEpisodes)
                assertEquals(HomeEffects.NavigateToEpisodes, awaitItem())
            }
        }

    @Test
    fun `when ClickOnLocations event is call check that NavigateToCharacters effect is emit`() =
        runTest {
            viewModel.effects.test {
                viewModel.onEvents(HomeEvents.ClickOnLocations)
                assertEquals(HomeEffects.NavigateToLocations, awaitItem())
            }
        }

    @Test
    fun `when ClickOnSettings event is call check that NavigateToCharacters effect is emit`() =
        runTest {
            viewModel.effects.test {
                viewModel.onEvents(HomeEvents.ClickOnSettings)
                assertEquals(HomeEffects.NavigateToSettings, awaitItem())
            }
        }

}
