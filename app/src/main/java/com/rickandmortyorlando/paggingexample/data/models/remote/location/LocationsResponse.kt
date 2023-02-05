package com.rickandmortyorlando.paggingexample.data.models.remote.location

import com.rickandmortyorlando.paggingexample.data.models.remote.character.Info

data class LocationsResponse(
    val info: Info,
    val results:List<SingleLocation>,
)