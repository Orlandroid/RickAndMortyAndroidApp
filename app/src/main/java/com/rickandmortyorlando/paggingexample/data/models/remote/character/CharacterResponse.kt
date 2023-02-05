package com.rickandmortyorlando.paggingexample.data.models.remote.character

data class CharacterResponse(
    val info: Info,
    val results:List<Character>
)
