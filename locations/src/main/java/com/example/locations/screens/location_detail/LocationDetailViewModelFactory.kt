package com.example.locations.screens.location_detail

import dagger.assisted.AssistedFactory

@AssistedFactory
interface LocationDetailViewModelFactory {
    fun create(locationId: Int): LocationDetailViewModel
}