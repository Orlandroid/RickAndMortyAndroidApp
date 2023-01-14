package com.example.paggingexample.data.models.remote.location

import com.example.paggingexample.data.models.remote.character.Info

data class LocationsResponse(
    val info: Info,
    val results:List<SingleLocation>,
)