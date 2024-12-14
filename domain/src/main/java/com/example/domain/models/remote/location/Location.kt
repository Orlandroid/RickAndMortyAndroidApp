package com.example.domain.models.remote.location

import com.example.domain.models.remote.character.Location

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
    name = name,
    url = url,
    dimension = dimension,
    created = created,
    type = type
)
