package com.rickandmortyorlando.orlando.features.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.domain.repository.LocationRepository
import com.rickandmortyorlando.orlando.features.many_episodes.ManyEpisodesEffects
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LocationEvents {
    data class OnLocationClicked(val locationId: Int) : LocationEvents()
}

sealed class LocationEffects {
    data class NavigateToLocationDetail(val locationId: Int) : LocationEffects()
}


@HiltViewModel
class LocationsViewModel @Inject constructor(
    locationsRepository: LocationRepository
) : ViewModel() {

    private val _effects = Channel<LocationEffects>()

    val effects = _effects.receiveAsFlow()

    val locations = locationsRepository.getLocations().cachedIn(viewModelScope)

    fun onEvents(event: LocationEvents) {
        when (event) {
            is LocationEvents.OnLocationClicked -> {
                viewModelScope.launch {
                    _effects.send(LocationEffects.NavigateToLocationDetail(event.locationId))
                }
            }
        }
    }

}