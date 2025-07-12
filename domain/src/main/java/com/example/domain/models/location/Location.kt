package com.example.domain.models.location

data class Location(
    val id: Int,
    val name: String,
    val url: String,
    val dimension: String,
    val created: String,
    val residents: List<String>,
    val type: String
) {

    companion object {
        fun mockLocation() = Location(
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

fun Location.getPairInfoLocation() = listOf(
    Pair("Location Name", name),
    Pair("Type", type),
    Pair("Dimension", dimension),
    Pair("Create", created),
)
