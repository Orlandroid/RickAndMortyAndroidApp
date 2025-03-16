package com.example.data.model.desirializer

import com.example.data.model.episode.EpisodeData
import com.example.data.model.episode.EpisodeResponse
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class ResponseDeserializer : JsonDeserializer<List<EpisodeData>> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): List<EpisodeData>? {
        val jsonObject = json?.asJsonObject
        val airDate = jsonObject?.get("air_date")?.asString.orEmpty()
        val created = jsonObject?.get("created")?.asString.orEmpty()
        val episode = jsonObject?.get("episode")?.asString.orEmpty()
        val id = jsonObject?.get("id")?.asInt ?: -1
        val name = jsonObject?.get("name")?.asString.orEmpty()
        val url = jsonObject?.get("url")?.asString.orEmpty()
        val characters = jsonObject?.get("characters")?.asJsonArray ?: emptyList<String>()
        val episodeDate = EpisodeData(
            air_date = airDate,
            characters = emptyList(),
            created = created,
            episode = episode,
            id = id,
            name = name,
            url = url
        )
        return if (jsonObject?.isJsonObject == true) {
            listOf(episodeDate)
        }else{
            context?.deserialize(jsonObject,EpisodeResponse::class.java)
        }
    }
}