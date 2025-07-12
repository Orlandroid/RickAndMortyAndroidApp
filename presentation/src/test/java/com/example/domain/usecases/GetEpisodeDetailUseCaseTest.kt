package com.example.domain.usecases

import android.annotation.SuppressLint
import com.example.domain.models.characters.Character
import com.example.domain.models.episodes.Episode
import com.example.domain.models.episodes.EpisodeImage
import com.example.domain.repository.CharacterRepository
import com.example.domain.repository.EpisodesRepository
import com.example.domain.state.ApiResult
import com.example.domain.state.getData
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetEpisodeDetailUseCaseTest {


    private lateinit var useCase: GetEpisodeDetailUseCase
    private val characterRepository: CharacterRepository = mockk(relaxed = true)
    private val episodesRepository: EpisodesRepository = mockk(relaxed = true)
    private val character: Character = Character.mockCharacter()


    @Before
    fun setup() {
        useCase = GetEpisodeDetailUseCase(
            characterRepository = characterRepository,
            episodesRepository = episodesRepository
        )
    }

    @Test
    fun `when getEpisode fails we return early and the 2 remaining services are not called`() =
        runTest {
            coEvery { episodesRepository.getEpisode(any()) } returns ApiResult.Error(msg = "")
            val result = useCase.invoke("1")
            coVerify(exactly = 1) { episodesRepository.getEpisode(any()) }
            coVerify(inverse = true) { characterRepository.getManyCharacters(any()) }
            coVerify(inverse = true) { episodesRepository.getImageOfEpisode(any()) }
            assertThat(result is ApiResult.Error)
        }

    @SuppressLint("CheckResult")
    @Test
    fun `when getEpisode, getImageOfEpisode, getManyCharacters return success`() =
        runTest {
            val expectedUseCaseResponse = GetEpisodeDetailUseCase.EpisodeDetail(
                episode = Episode.mockEpisode(),
                episodeImage = EpisodeImage.mockEpisodeIMage(),
                characters = listOf(character)
            )
            coEvery { episodesRepository.getEpisode(any()) } returns ApiResult.Success(Episode.mockEpisode())
            coEvery { episodesRepository.getImageOfEpisode(any()) } returns ApiResult.Success(
                EpisodeImage.mockEpisodeIMage()
            )
            coEvery { characterRepository.getManyCharacters(any()) } returns ApiResult.Success(
                listOf(character)
            )
            val result = useCase.invoke("1")
            coVerify(exactly = 1) { episodesRepository.getEpisode(any()) }
            coVerify(exactly = 1) { characterRepository.getManyCharacters(any()) }
            coVerify(exactly = 1) { episodesRepository.getImageOfEpisode(any()) }
            assertThat(result is ApiResult.Success)
            val data = result.getData()
            assertThat(data).isEqualTo(expectedUseCaseResponse)
        }

    @SuppressLint("CheckResult")
    @Test
    fun `when getEpisode return success and getImageOfEpisode,getManyCharacters fails`() =
        runTest {
            val expectedUseCaseResponse = GetEpisodeDetailUseCase.EpisodeDetail(
                episode = Episode.mockEpisode()
            )
            coEvery { episodesRepository.getEpisode(any()) } returns ApiResult.Success(Episode.mockEpisode())
            coEvery { episodesRepository.getImageOfEpisode(any()) } returns ApiResult.Error("")
            coEvery { characterRepository.getManyCharacters(any()) } returns ApiResult.Error("")
            val result = useCase.invoke("1")
            coVerify(exactly = 1) { episodesRepository.getEpisode(any()) }
            coVerify(exactly = 1) { characterRepository.getManyCharacters(any()) }
            coVerify(exactly = 1) { episodesRepository.getImageOfEpisode(any()) }
            assertThat(result is ApiResult.Success)
            val data = result.getData()
            assertThat(data).isEqualTo(expectedUseCaseResponse)
        }

    @SuppressLint("CheckResult")
    @Test
    fun `when getEpisode,getImageOfEpisode return success and getManyCharacters fails`() =
        runTest {
            val expectedUseCaseResponse = GetEpisodeDetailUseCase.EpisodeDetail(
                episode = Episode.mockEpisode(),
                episodeImage = EpisodeImage.mockEpisodeIMage()
            )
            coEvery { episodesRepository.getEpisode(any()) } returns ApiResult.Success(Episode.mockEpisode())
            coEvery { episodesRepository.getImageOfEpisode(any()) } returns ApiResult.Success(
                EpisodeImage.mockEpisodeIMage()
            )
            coEvery { characterRepository.getManyCharacters(any()) } returns ApiResult.Error("")
            val result = useCase.invoke("1")
            coVerify(exactly = 1) { episodesRepository.getEpisode(any()) }
            coVerify(exactly = 1) { characterRepository.getManyCharacters(any()) }
            coVerify(exactly = 1) { episodesRepository.getImageOfEpisode(any()) }
            assertThat(result is ApiResult.Success)
            val data = result.getData()
            assertThat(data).isEqualTo(expectedUseCaseResponse)
        }

    @SuppressLint("CheckResult")
    @Test
    fun `when getEpisode,getManyCharacters return success and getImageOfEpisode fails`() =
        runTest {
            val expectedUseCaseResponse = GetEpisodeDetailUseCase.EpisodeDetail(
                episode = Episode.mockEpisode(),
                characters = listOf(character)
            )
            coEvery { episodesRepository.getEpisode(any()) } returns ApiResult.Success(Episode.mockEpisode())
            coEvery { episodesRepository.getImageOfEpisode(any()) } returns ApiResult.Error()
            coEvery { characterRepository.getManyCharacters(any()) } returns ApiResult.Success(
                listOf(character)
            )
            val result = useCase.invoke("1")
            coVerify(exactly = 1) { episodesRepository.getEpisode(any()) }
            coVerify(exactly = 1) { characterRepository.getManyCharacters(any()) }
            coVerify(exactly = 1) { episodesRepository.getImageOfEpisode(any()) }
            assertThat(result is ApiResult.Success)
            val data = result.getData()
            assertThat(data).isEqualTo(expectedUseCaseResponse)
        }
}