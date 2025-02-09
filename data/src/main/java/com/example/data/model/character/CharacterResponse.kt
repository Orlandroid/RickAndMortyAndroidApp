package com.example.data.model.character

import com.example.data.model.Info
import com.example.domain.models.characters.Character
import com.example.domain.models.location.Location

data class CharacterResponse(
    val info: Info,
    val results: List<CharacterData>
)

data class CharacterData(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
)


fun CharacterData.toCharacter() = Character(
    id = id,
    image = image,
    name = name,
    status = status,
    species = species,
    gender = gender,
    episode = episode,
    urlLocation = location.url
)
