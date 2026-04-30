package com.rickandmortyorlando.orlando.features.location_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.di.IoDispatcher
import com.example.domain.models.characters.Character
import com.example.domain.models.location.Location
import com.example.domain.state.getData
import com.example.domain.state.getMessage
import com.example.domain.state.isError
import com.example.domain.usecases.GetLocationDetailUseCase
import com.rickandmortyorlando.orlando.features.locations.LocationEffects
import com.rickandmortyorlando.orlando.features.locations.LocationEvents
import com.rickandmortyorlando.orlando.state.BaseViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class LocationDetailEvents {
    data class OnCharacterClicked(val characterId: Int) : LocationDetailEvents()
}

sealed class LocationDetailEffects {
    data class NavigateToCharacterDetail(val characterId: Int) : LocationDetailEffects()
}

data class LocationDetailUiState(
    val location: Location,
    val characters: List<Character>
)

@HiltViewModel
class LocationDetailViewModel @Inject constructor(
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getLocationDetailUseCase: GetLocationDetailUseCase
) : ViewModel() {


    private val _state =
        MutableStateFlow<BaseViewState<LocationDetailUiState>>(BaseViewState.Loading)
    val state = _state.asStateFlow()

    private val _effects = Channel<LocationDetailEffects>()

    val effects = _effects.receiveAsFlow()


    fun onEvents(event: LocationDetailEvents) {
        when (event) {
            is LocationDetailEvents.OnCharacterClicked -> {
                viewModelScope.launch {
                    _effects.send(LocationDetailEffects.NavigateToCharacterDetail(event.characterId))
                }
            }
        }
    }

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