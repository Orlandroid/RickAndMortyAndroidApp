package com.example.domain.models.remote.character

data class Character(
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

data class CharacterMin(
    val image: String,
    val name: String,
    val status: String,
    val species: String
)

fun Character.toMin() = CharacterMin(image, name, status, species)