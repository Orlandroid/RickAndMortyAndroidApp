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
        fun mockLocation(residents: Residents = Residents.Empty) = Location(
            id = 5,
            name = "Anatomy Park",
            url = "https://rickandmortyapi.com/api/location/5",
            dimension = "Dimension C-137",
            created = "2017-11-10T13:08:46.060Z",
            type = "Microverse",
            residents = when (residents) {
                Residents.Empty -> emptyList()
                Residents.Single -> listOf(getUrlResident())
                Residents.MoreThanOne -> listOf(getUrlResident(), getUrlResident())
            }
        )

        private fun getUrlResident() = "https://rickandmortyapi.com/api/character/1"
    }

    sealed class Residents {
        data object Empty : Residents()
        data object Single : Residents()
        data object MoreThanOne : Residents()
    }
}

fun Location.getPairInfoLocation() = listOf(
    Pair("Location Name", name),
    Pair("Type", type),
    Pair("Dimension", dimension),
    Pair("Create", created),
)
