package com.rickandmortyorlando.orlando.features.settings

import com.example.data.preferences.RickAndMortyPreferences
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
            viewModel.handleEvents(SettingsEvents.OnToggle(isEnable = true))
            val uiState = viewModel.uiState
            assert(uiState.value.isNightModeEnable)
        }

    @Test
    fun `when OnToggle event is call  with false param check that state isEnable = false `() =
        runTest {
            viewModel.handleEvents(SettingsEvents.OnToggle(isEnable = false))
            val uiState = viewModel.uiState
            assert(uiState.value.isNightModeEnable.not())
        }

    @Test
    fun `when init check that initial state of isEnable take the correct value from preferences `() =
        runTest {
            every { rickAndMortyPreferences.getIsNightMode() } returns true
            var lastState: SettingsUiState? = null
            val job = launch {
                viewModel.uiState.collectLatest {
                    lastState = it
                }
            }
            job.join()
            assert(lastState!!.isNightModeEnable)
        }

}