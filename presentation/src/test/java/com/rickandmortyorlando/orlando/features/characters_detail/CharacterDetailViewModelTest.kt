package com.rickandmortyorlando.orlando.features.characters_detail

import com.example.domain.models.characters.Character
import com.example.domain.models.location.Location
import com.example.domain.repository.CharacterRepository
import com.example.domain.state.ApiResult
import com.example.domain.usecases.GetCharacterDetailUseCase
import com.rickandmortyorlando.orlando.state.BaseViewState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class CharacterDetailViewModelTest {

    private lateinit var characterDetailViewModel: CharacterDetailViewModel
    private val characterDetailUseCase = mockk<GetCharacterDetailUseCase>()
    private val characterRepository = mockk<CharacterRepository>()

    @Before
    fun setup() {
        characterDetailViewModel = CharacterDetailViewModel(
            ioDispatcher = UnconfinedTestDispatcher(),
            characterDetailUseCase = characterDetailUseCase
        )
    }

    @Test
    fun `when we get the initial state this should be loading`() {
        val state = characterDetailViewModel.state
        assert(state.value == BaseViewState.Loading)
    }

    @Test
    fun `when use case  return data state should be BaseViewState Content`() = runTest {
        val mockCharacterDetail = GetCharacterDetailUseCase.CharacterDetail(
            characterDetail = Character.mockCharacter(),
            location = Location.mockLocation(),
            idsOfEpisodes = "1,2,3"
        )
        coEvery { characterDetailUseCase.invoke(any()) } returns ApiResult.Success(
            mockCharacterDetail
        )

        characterDetailViewModel.getCharacterDetailInfo(5)

        val state = characterDetailViewModel.state.value as BaseViewState.Content
        assert(state.result == mockCharacterDetail.toCharacterDetail())
    }


    @Test
    fun `when use case  return error state should be BaseViewState Error`() = runTest {
        val mockCharacterDetail = GetCharacterDetailUseCase.CharacterDetail(
            characterDetail = Character.mockCharacter(),
            idsOfEpisodes = "1,2,3"
        )
        coEvery { characterRepository.getCharacter(any()) } throws Throwable(message = "")
        coEvery { characterDetailUseCase.invoke(any()) } returns ApiResult.Success(
            mockCharacterDetail
        )

        characterDetailViewModel.getCharacterDetailInfo(0)

        val state = characterDetailViewModel.state.value as BaseViewState.Content
        assert(state.result == mockCharacterDetail.toCharacterDetail())
    }


}