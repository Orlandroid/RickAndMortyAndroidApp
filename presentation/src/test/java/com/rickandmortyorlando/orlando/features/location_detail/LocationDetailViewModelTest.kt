package com.rickandmortyorlando.orlando.features.location_detail

import com.example.domain.models.characters.Character
import com.example.domain.models.location.Location
import com.example.domain.state.ApiResult
import com.example.domain.state.FAIL_RESPONSE_FROM_SERVER
import com.example.domain.usecases.GetLocationDetailUseCase
import com.rickandmortyorlando.orlando.state.BaseViewState
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import com.google.common.truth.Truth.assertThat


@OptIn(ExperimentalCoroutinesApi::class)
class LocationDetailViewModelTest {

    private lateinit var viewModel: LocationDetailViewModel
    private val getLocationDetailUSeCase: GetLocationDetailUseCase = mockk()

    @Before
    fun setup() {
        viewModel = LocationDetailViewModel(
            ioDispatcher = UnconfinedTestDispatcher(),
            getLocationDetailUseCase = getLocationDetailUSeCase
        )
    }

    @Test
    fun `when we get the initial state this should be loading`() {
        val state = viewModel.state
        assertThat(state.value).isEqualTo(BaseViewState.Loading)
    }

    @Test
    fun `should emit Error state when GetLocationDetailUseCase throws exception`() = runTest {
        coEvery { getLocationDetailUSeCase.invoke(locationId = any()) } returns ApiResult.Error(msg = FAIL_RESPONSE_FROM_SERVER)

        viewModel.getLocationDetail(1)

        val state = viewModel.state.value
        assert(state is BaseViewState.Error)
        assert((state as BaseViewState.Error).message == FAIL_RESPONSE_FROM_SERVER)
    }

    @Test
    fun `should emit Content state when GetLocationDetailUseCase case returns success`() = runTest {

        val detail = GetLocationDetailUseCase.LocationDetail(
            location = Location.mockLocation(),
            Character.getCharacters(8)
        )

        coEvery { getLocationDetailUSeCase.invoke(any()) } returns ApiResult.Success(detail)

        viewModel.getLocationDetail(1)

        val state = viewModel.state.value
        assert(state is BaseViewState.Content)
        val result = (state as BaseViewState.Content).result
        assertEquals(result, detail.toUi())
    }


}