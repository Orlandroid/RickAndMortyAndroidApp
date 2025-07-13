package com.rickandmortyorlando.orlando.features.location_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.di.IoDispatcher
import com.example.domain.models.characters.Character
import com.example.domain.models.location.Location
import com.example.domain.state.getData
import com.example.domain.state.getMessage
import com.example.domain.state.isError
import com.example.domain.usecases.GetLocationDetailUseCase
import com.rickandmortyorlando.orlando.state.BaseViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class LocationDetailUiState(
    val location: Location,
    val characters: List<Character>
)

fun GetLocationDetailUseCase.LocationDetail.toUi() =
    LocationDetailUiState(location = location, characters = characters ?: emptyList())

@HiltViewModel
class LocationDetailViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getLocationDetailUseCase: GetLocationDetailUseCase
) : ViewModel() {


    private val _state =
        MutableStateFlow<BaseViewState<LocationDetailUiState>>(BaseViewState.Loading)
    val state = _state.asStateFlow()

    fun getLocationDetail(locationId: Int) = viewModelScope.launch(ioDispatcher) {
        val locationDetail = getLocationDetailUseCase.invoke(locationId)
        if (locationDetail.isError()) {
            _state.value = BaseViewState.Error(message = locationDetail.getMessage())
            return@launch
        }
        val location = locationDetail.getData()
        _state.value =
            BaseViewState.Content(
                LocationDetailUiState(
                    location = location.location,
                    characters = location.characters ?: emptyList()
                )
            )
    }

}