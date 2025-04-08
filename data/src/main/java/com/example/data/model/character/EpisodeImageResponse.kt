package com.example.data.model.character

import com.example.domain.models.episodes.EpisodeImage

data class EpisodeImageResponse(
    val id: Int,
    val url: String,
    val name: String,
    val season: Int,
    val number: Int,
    val type: String,
    val rating: Rating,
    val image: Image,
    val summary: String
) {
    data class Rating(
        val average: Double?
    )

    data class Image(
        val original: String,
    )
}

fun EpisodeImageResponse.toEpisodeImage() = EpisodeImage(
    id = id,
    url = url,
    name = name,
    season = season,
    number = number,
    type = type,
    average = rating.average,
    imageUrl = image.original,
    summary = summary
)