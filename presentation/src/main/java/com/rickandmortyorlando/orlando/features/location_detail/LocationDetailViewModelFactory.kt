package com.rickandmortyorlando.orlando.features.location_detail

import dagger.assisted.AssistedFactory

@AssistedFactory
interface LocationDetailViewModelFactory {
    fun create(locationId: Int): LocationDetailViewModel
}