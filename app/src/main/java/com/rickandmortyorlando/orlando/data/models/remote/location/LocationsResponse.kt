package com.rickandmortyorlando.orlando.data.models.remote.location

import com.rickandmortyorlando.orlando.data.models.remote.character.Info

data class LocationsResponse(
    val info: Info,
    val results:List<SingleLocation>,
)