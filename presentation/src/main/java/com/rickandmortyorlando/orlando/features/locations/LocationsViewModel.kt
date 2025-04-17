package com.rickandmortyorlando.orlando.features.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.domain.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LocationsViewModel @Inject constructor(
    locationsRepository: LocationRepository
) : ViewModel() {

    val locations = locationsRepository.getLocations().cachedIn(viewModelScope)

}