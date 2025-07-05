package com.example.domain.usecases


import com.example.domain.repository.CharacterRepository
import com.example.domain.repository.LocationRepository
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetCharacterDetailUseCaseTest {

    private lateinit var useCase: GetCharacterDetailUseCase
    private val characterRepository: CharacterRepository = mockk()
    private val locationsRepository: LocationRepository = mockk()


    @Before
    fun setup() {
        useCase = GetCharacterDetailUseCase(
            characterRepository = characterRepository,
            locationsRepository = locationsRepository
        )
    }


    @Test
    fun `when`() =
        runTest {

        }
}