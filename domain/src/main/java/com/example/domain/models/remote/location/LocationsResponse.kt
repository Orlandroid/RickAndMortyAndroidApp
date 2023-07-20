package com.example.domain.models.remote.location

import com.example.domain.models.remote.character.Info

data class LocationsResponse(
    val info: Info,
    val results:List<SingleLocation>,
)