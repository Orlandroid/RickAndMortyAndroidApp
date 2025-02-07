package com.example.data.model.location

import com.example.data.model.Info
import com.example.domain.models.characters.Location

data class LocationsResponse(
    val info: Info,
    val results: List<SingleLocation>,
)

data class SingleLocation(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String
)

fun SingleLocation.toLocation() = Location(
    id = id,
    name = name,
    url = url,
    dimension = dimension,
    created = created,
    type = type
)
