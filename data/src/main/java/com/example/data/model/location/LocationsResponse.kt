package com.example.data.model.location

import com.example.data.model.Info
import com.example.domain.models.location.Location

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
) {
    companion object {
        fun getMockSingleLocation() = SingleLocation(
            id = 5,
            name = "Anatomy Park",
            url = "https://rickandmortyapi.com/api/location/5",
            dimension = "Dimension C-137",
            created = "2017-11-10T13:08:46.060Z",
            type = "Microverse",
            residents = emptyList()
        )
    }
}

fun SingleLocation.toLocation() = Location(
    id = id,
    name = name,
    url = url,
    dimension = dimension,
    created = created,
    type = type,
    residents = residents
)
