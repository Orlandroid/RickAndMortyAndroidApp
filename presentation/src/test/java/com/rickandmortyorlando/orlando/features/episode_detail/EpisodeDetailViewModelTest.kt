package com.rickandmortyorlando.orlando.features.episode_detail

import com.example.domain.models.characters.Character
import com.example.domain.models.episodes.Episode
import com.example.domain.models.episodes.EpisodeImage
import com.example.domain.usecases.GetEpisodeDetailUseCase
import com.rickandmortyorlando.orlando.state.BaseViewState
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class EpisodeDetailViewModelTest {

    private val getEpisodeDetailUseCase: GetEpisodeDetailUseCase = mockk()
    private lateinit var viewModel: EpisodeDetailViewModel

    @Before
    fun setup() {
        viewModel = EpisodeDetailViewModel(UnconfinedTestDispatcher(), getEpisodeDetailUseCase)
    }

    @Test
    fun `should emit Content state when GetEpisodeDetailUseCase case returns data`() = runTest {
        val episodeId = "1"
        val episode = Episode.mockEpisode()

        val episodeImage = EpisodeImage.mockEpisodeIMage()
        val characters = Character.getCharacters(8)

        val detail = GetEpisodeDetailUseCase.EpisodeDetail(episode, characters, episodeImage)

        coEvery { getEpisodeDetailUseCase.invoke(episodeId) } returns detail

        viewModel.getEpisodeDetail(episodeId)

        val state = viewModel.state.value as BaseViewState.Content


        assertEquals(episode, state.result.episode)
        assertEquals(characters, state.result.characters)
        assertEquals(episodeImage, state.result.episodeImage)
    }

    @Test
    fun `should emit Error state when use case throws exception`() = runTest {
        val episodeId = "1"
        coEvery { getEpisodeDetailUseCase.invoke(episodeId) } throws RuntimeException("Network error")

        viewModel.getEpisodeDetail(episodeId)

        val state = viewModel.state.value
        assert(state is BaseViewState.Error)
    }

    @Test
    fun `when init then state of state should be Loading`() {
        assertEquals(viewModel.state.value, BaseViewState.Loading)
    }
}
