package com.rickandmortyorlando.orlando.features.settings

import app.cash.turbine.test
import com.example.data.preferences.RickAndMortyPreferences
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {

    private lateinit var viewModel: SettingsViewModel
    private val rickAndMortyPreferences = mockk<RickAndMortyPreferences>(relaxed = true)

    @Before
    fun setup() {
        every { rickAndMortyPreferences.getIsNightMode() } returns true
        viewModel = SettingsViewModel(rickAndMortyPreferences)
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when OnToggle event is call  with true param check that state isEnable = true `() =
        runTest {
            val viewModel = spyk(objToCopy = viewModel, recordPrivateCalls = true)
            viewModel.handleEvents(SettingsEvents.OnToggle(isEnable = true))
            val uiState = viewModel.uiState
            assertThat(uiState.value.isNightModeEnable).isTrue()
            verify(exactly = 1) { viewModel["changeTheme"].invoke(true) }
        }

    @Test
    fun `when OnToggle event is call  with false param check that state isEnable = false `() =
        runTest {
            viewModel.handleEvents(SettingsEvents.OnToggle(isEnable = false))
            val uiState = viewModel.uiState
            assertThat(uiState.value.isNightModeEnable).isFalse()
        }

    @Test
    fun `when init check that initial state of isEnable take the correct value from preferences `() =
        runTest {
            viewModel.uiState.test {
                assertThat(awaitItem().isNightModeEnable).isTrue()
            }
        }

}