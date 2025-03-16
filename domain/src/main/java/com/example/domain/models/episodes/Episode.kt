package com.example.domain.models.episodes

data class Episode(
    val airDate: String,
    val characters: List<String>,
    val created: String,
    val episode: String,
    val id: Int,
    val name: String,
    val url: String
) {
    companion object {
        fun mockEpisode() =
            Episode(
                airDate = "",
                characters = emptyList(),
                created = "Decembre 9, 2013",
                episode = "S01E02",
                id = 0,
                name = "Lawnmower",
                url = ""
            )
    }

}