package com.example.domain.usecases

import com.google.common.truth.Truth.assertThat
import com.example.domain.models.characters.Character
import com.example.domain.models.location.Location
import com.example.domain.models.location.Location.Residents
import com.example.domain.repository.CharacterRepository
import com.example.domain.repository.LocationRepository
import com.example.domain.state.ApiResult
import com.example.domain.state.getData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetLocationDetailUseCaseTest {

    private lateinit var useCase: GetLocationDetailUseCase
    private val characterRepository: CharacterRepository = mockk(relaxed = true)
    private val locationRepository: LocationRepository = mockk(relaxed = true)
    private val mockLocationNotResidents = Location.mockLocation()
    private val mockLocationSingleResident = Location.mockLocation(Residents.Single)
    private val mockLocationMoreThanOneResident = Location.mockLocation(Residents.MoreThanOne)


    @Before
    fun setup() {
        useCase = GetLocationDetailUseCase(
            characterRepository = characterRepository,
            locationRepository = locationRepository
        )
    }

    @Test
    fun `when locationRepository getLocation fails result should be Error`() =
        runTest {
            coEvery { locationRepository.getLocation(any()) } returns ApiResult.Error(msg = "")
            val result = useCase.invoke(1)
            coVerify(exactly = 1) { locationRepository.getLocation(any()) }
            coVerify(inverse = true) { characterRepository.getManyCharacters(any()) }
            coVerify(inverse = true) { characterRepository.getCharacter(any()) }
            assert(result is ApiResult.Error)
        }

    @Test
    fun `when locationRepository getLocation success and location has not residents should return Error`() =
        runTest {
            coEvery { locationRepository.getLocation(any()) } returns ApiResult.Success(
                mockLocationNotResidents
            )
            val result = useCase.invoke(1)
            coVerify(exactly = 1) { locationRepository.getLocation(any()) }
            coVerify(inverse = true) { characterRepository.getCharacter(any()) }
            coVerify(inverse = true) { characterRepository.getManyCharacters(any()) }
            assert(result is ApiResult.Error)
        }

    @Test
    fun `when locationRepository getLocation,getCharacter success and locations has one resident`() =
        runTest {
            val expectedResult = GetLocationDetailUseCase.LocationDetail(
                location = mockLocationSingleResident,
                characters = listOf(Character.mockCharacter())
            )
            coEvery { locationRepository.getLocation(any()) } returns ApiResult.Success(
                mockLocationSingleResident
            )
            coEvery { characterRepository.getCharacter(any()) } returns ApiResult.Success(
                Character.mockCharacter()
            )
            val result = useCase.invoke(1)
            coVerify(exactly = 1) { locationRepository.getLocation(any()) }
            coVerify(exactly = 1) { characterRepository.getCharacter(any()) }
            coVerify(inverse = true) { characterRepository.getManyCharacters(any()) }
            assert(result is ApiResult.Success)
            assertThat(result.getData()).isEqualTo(expectedResult)
        }

    @Test
    fun `when  getLocation success and locations has one resident and getCharacter fails`() =
        runTest {
            val expectedResult = GetLocationDetailUseCase.LocationDetail(
                location = mockLocationSingleResident
            )
            coEvery { locationRepository.getLocation(any()) } returns ApiResult.Success(
                mockLocationSingleResident
            )
            coEvery { characterRepository.getCharacter(any()) } returns ApiResult.Error()
            val result = useCase.invoke(1)
            coVerify(exactly = 1) { locationRepository.getLocation(any()) }
            coVerify(exactly = 1) { characterRepository.getCharacter(any()) }
            coVerify(inverse = true) { characterRepository.getManyCharacters(any()) }
            assert(result is ApiResult.Success)
            assertThat(result.getData()).isEqualTo(expectedResult)
        }

    @Test
    fun `when  getLocation success and locations has more that one resident and getManyCharacters success`() =
        runTest {
            val expectedResult = GetLocationDetailUseCase.LocationDetail(
                location = mockLocationMoreThanOneResident,
                characters = listOf(Character.mockCharacter())
            )
            coEvery { locationRepository.getLocation(any()) } returns ApiResult.Success(
                mockLocationMoreThanOneResident
            )
            coEvery { characterRepository.getManyCharacters(any()) } returns ApiResult.Success(
                listOf(Character.mockCharacter())
            )
            val result = useCase.invoke(1)
            coVerify(exactly = 1) { locationRepository.getLocation(any()) }
            coVerify(inverse = true) { characterRepository.getCharacter(any()) }
            coVerify(exactly = 1) { characterRepository.getManyCharacters(any()) }
            assert(result is ApiResult.Success)
            assertThat(result.getData()).isEqualTo(expectedResult)
        }

    @Test
    fun `when  getLocation success and locations has more that one resident and getManyCharacters fail`() =
        runTest {
            val expectedResult = GetLocationDetailUseCase.LocationDetail(
                location = mockLocationMoreThanOneResident
            )
            coEvery { locationRepository.getLocation(any()) } returns ApiResult.Success(
                mockLocationMoreThanOneResident
            )
            coEvery { characterRepository.getManyCharacters(any()) } returns ApiResult.Error()
            val result = useCase.invoke(1)
            coVerify(exactly = 1) { locationRepository.getLocation(any()) }
            coVerify(inverse = true) { characterRepository.getCharacter(any()) }
            coVerify(exactly = 1) { characterRepository.getManyCharacters(any()) }
            assert(result is ApiResult.Success)
            assertThat(result.getData()).isEqualTo(expectedResult)
        }
}