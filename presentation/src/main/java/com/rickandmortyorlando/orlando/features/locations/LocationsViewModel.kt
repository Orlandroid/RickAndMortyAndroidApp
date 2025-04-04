package com.rickandmortyorlando.orlando.features.locations

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.di.CoroutineDispatchers
import com.example.domain.repository.LocationsRepository
import com.rickandmortyorlando.orlando.features.base.BaseViewModel
import com.rickandmortyorlando.orlando.features.main.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LocationsViewModel @Inject constructor(
    locationsRepository: LocationsRepository,
    networkHelper: NetworkHelper,
    coroutineDispatcher: CoroutineDispatchers
) : BaseViewModel(networkHelper = networkHelper, coroutineDispatchers = coroutineDispatcher) {

    val locations = locationsRepository.getLocations().cachedIn(viewModelScope)

}