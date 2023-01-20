package com.example.paggingexample.data.models.remote.character

data class CharacterResponse(
    val info: Info,
    val results:List<Character>
)
