package com.example.domain.models.episodes

data class EpisodeImage(
    val id: Int,
    val url: String,
    val name: String,
    val season: Int,
    val number: Int,
    val type: String,
    val average: Double?,
    val imageUrl: String,
    val summary: String
) {
    companion object{
        fun mockEpisodeIMage() = EpisodeImage(
            imageUrl = "https://static.tvmaze.com/uploads/images/medium_landscape/292/730352.jpg",
            url = "",
            name = "",
            season = 1,
            number = 1,
            type = "",
            average = 0.0,
            id = 0,
            summary = ""
        )
    }
}