package com.rickandmortyorlando.orlando.features.many_episodes

import app.cash.turbine.test
import com.example.domain.models.episodes.Episode
import com.example.domain.repository.EpisodesRepository
import com.example.domain.state.ApiResult
import com.example.domain.state.FAIL_RESPONSE_FROM_SERVER
import com.google.common.truth.Truth.assertThat
import com.rickandmortyorlando.orlando.state.BaseViewState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ManyEpisodesViewModelTest {

    private lateinit var manyEpisodesViewModel: ManyEpisodesViewModel
    private val repository: EpisodesRepository = mockk()

    @Before
    fun setup() {
        manyEpisodesViewModel = ManyEpisodesViewModel(
            repository = repository
        )
    }

    @Test
    fun `when we get the initial state this should be loading`() = runTest {
        manyEpisodesViewModel.state.test {
            assertThat(awaitItem()).isEqualTo(BaseViewState.Loading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should emit Error state when GetLocationDetailUseCase throws exception`() = runTest {
        coEvery { repository.getManyEpisodes(ids = any()) } returns ApiResult.Error(msg = FAIL_RESPONSE_FROM_SERVER)

        manyEpisodesViewModel.getEpisodes("1,2,3")

        manyEpisodesViewModel.state.test {
            val errorEmission = awaitItem()
            assertThat(errorEmission).isInstanceOf(BaseViewState.Error::class.java)
            assertThat(errorEmission).isEqualTo(BaseViewState.Error(message = FAIL_RESPONSE_FROM_SERVER))
        }
    }

    @Test
    fun `should emit Content state when GetLocationDetailUseCase case returns success`() = runTest {
        val mockListOfEpisodes = listOf(
            Episode.mockEpisode()
        )
        coEvery { repository.getManyEpisodes(ids = any()) } returns ApiResult.Success(
            mockListOfEpisodes
        )

        manyEpisodesViewModel.getEpisodes("1")

        manyEpisodesViewModel.state.test {
            val loadingState = awaitItem()
            assertThat(loadingState).isInstanceOf(BaseViewState.Loading::class.java)
            val contentState = awaitItem()
            assertThat(contentState).isInstanceOf(BaseViewState.Content::class.java)
            val content = contentState as BaseViewState.Content
            assertThat(content).isEqualTo(BaseViewState.Content(result = listOf(mockListOfEpisodes)))
        }
    }
}