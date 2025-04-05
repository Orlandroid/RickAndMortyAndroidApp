package com.rickandmortyorlando.orlando.features.location_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.di.IoDispatcher
import com.example.domain.models.characters.Character
import com.example.domain.models.location.Location
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

@HiltViewModel
class LocationDetailViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getLocationDetailUseCase: GetLocationDetailUseCase
) : ViewModel() {


    private val _state = MutableStateFlow<BaseViewState<LocationDetailUiState>>(BaseViewState.Loading)
    val state = _state.asStateFlow()

    fun getLocationDetail(locationId: Int) = viewModelScope.launch(ioDispatcher) {
        runCatching {
            val locationDetail = getLocationDetailUseCase.invoke(locationId)
            _state.value =
                BaseViewState.Content(
                    LocationDetailUiState(
                        location = locationDetail.location,
                        characters = locationDetail.characters
                    )
                )
        }.onFailure {
            _state.value = BaseViewState.Error(message = it.message.orEmpty())
        }
    }

}