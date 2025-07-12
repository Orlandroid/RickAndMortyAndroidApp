package com.example.domain.usecases


import android.annotation.SuppressLint
import com.example.domain.models.characters.Character
import com.example.domain.models.location.Location
import com.example.domain.repository.CharacterRepository
import com.example.domain.repository.LocationRepository
import com.example.domain.state.ApiResult
import com.example.domain.state.getData
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetCharacterDetailUseCaseTest {

    private lateinit var useCase: GetCharacterDetailUseCase
    private val characterRepository: CharacterRepository = mockk(relaxed = true)
    private val locationsRepository: LocationRepository = mockk(relaxed = true)
    private val character: Character = Character.mockCharacter()


    @Before
    fun setup() {
        useCase = GetCharacterDetailUseCase(
            characterRepository = characterRepository,
            locationsRepository = locationsRepository
        )
    }


    @Test
    fun `when characterRepository getCharacter fails`() =
        runTest {
            coEvery { characterRepository.getCharacter(any()) } returns ApiResult.Error(msg = "")
            val result = useCase.invoke(1)
            coVerify(exactly = 1) { characterRepository.getCharacter(any()) }
            coVerify(inverse = true) { locationsRepository.getLocation(any()) }
            coVerify(inverse = true) { characterRepository.getManyCharacters(any()) }
            assertThat(result is ApiResult.Error)
        }

    @SuppressLint("CheckResult")
    @Test
    fun `when getCharacter,getLocation,getManyCharacters success`() =
        runTest {
            val expectedResult = GetCharacterDetailUseCase.CharacterDetail(
                characterDetail = character,
                location = Location.mockLocation(),
                charactersOfThisLocation = listOf(character),
                idsOfEpisodes = "1,2"
            )

            coEvery { characterRepository.getCharacter(any()) } returns ApiResult.Success(character)
            coEvery { locationsRepository.getLocation(any()) } returns ApiResult.Success(Location.mockLocation())
            coEvery { characterRepository.getManyCharacters(any()) } returns ApiResult.Success(
                listOf(character)
            )
            val result = useCase.invoke(1)
            coVerify(exactly = 1) { characterRepository.getCharacter(any()) }
            coVerify(exactly = 1) { locationsRepository.getLocation(any()) }
            coVerify(exactly = 1) { characterRepository.getManyCharacters(any()) }
            assertThat(result is ApiResult.Success)
            assertThat(result.getData()).isEqualTo(expectedResult)
        }


    @SuppressLint("CheckResult")
    @Test
    fun `when getCharacter,getLocation success and getManyCharacters fails`() =
        runTest {
            val expectedResult = GetCharacterDetailUseCase.CharacterDetail(
                characterDetail = character,
                location = Location.mockLocation(),
                idsOfEpisodes = "1,2"
            )

            coEvery { characterRepository.getCharacter(any()) } returns ApiResult.Success(character)
            coEvery { locationsRepository.getLocation(any()) } returns ApiResult.Success(Location.mockLocation())
            coEvery { characterRepository.getManyCharacters(any()) } returns ApiResult.Error()
            val result = useCase.invoke(1)
            coVerify(exactly = 1) { characterRepository.getCharacter(any()) }
            coVerify(exactly = 1) { locationsRepository.getLocation(any()) }
            coVerify(exactly = 1) { characterRepository.getManyCharacters(any()) }
            assertThat(result is ApiResult.Success)
            assertThat(result.getData()).isEqualTo(expectedResult)
        }

    @SuppressLint("CheckResult")
    @Test
    fun `when getCharacter,getManyCharacters success and getLocation fails`() =
        runTest {
            val expectedResult = GetCharacterDetailUseCase.CharacterDetail(
                characterDetail = character,
                idsOfEpisodes = "1,2"
            )

            coEvery { characterRepository.getCharacter(any()) } returns ApiResult.Success(character)
            coEvery { locationsRepository.getLocation(any()) } returns ApiResult.Error()
            coEvery { characterRepository.getManyCharacters(any()) } returns ApiResult.Success(
                listOf(character)
            )
            val result = useCase.invoke(1)
            coVerify(exactly = 1) { characterRepository.getCharacter(any()) }
            coVerify(exactly = 1) { locationsRepository.getLocation(any()) }
            coVerify(inverse = true) { characterRepository.getManyCharacters(any()) }
            assertThat(result is ApiResult.Success)
            assertThat(result.getData()).isEqualTo(expectedResult)
        }
}