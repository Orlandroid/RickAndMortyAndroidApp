package com.example.data.model.episode

import com.example.data.model.Info
import com.example.domain.models.episodes.Episode
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken

data class EpisodeResponse(
    val info: Info,
    val results: List<EpisodeData>
)

data class EpisodeData(
    val air_date: String,
    val characters: List<String>,
    val created: String,
    val episode: String,
    val id: Int,
    val name: String,
    val url: String
)

fun parseResponse(json: String): List<EpisodeData> {
    val gson = Gson()
    val jsonElement = JsonParser().parse(json)
    return if (jsonElement.isJsonArray) {
        gson.fromJson(json, object : TypeToken<List<EpisodeData>>() {}.type)
    } else {
        listOf(gson.fromJson(json, EpisodeData::class.java))
    }
}

fun EpisodeData.toEpisode() =
    Episode(
        airDate = air_date,
        characters = characters,
        created = created,
        episode = episode,
        id = id,
        name = name,
        url = url
    )