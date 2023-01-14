package com.example.paggingexample.data.models.remote.location.character

import com.example.paggingexample.data.models.remote.character.Info

data class CharacterResponse(
    val info: Info,
    val results:List<Character>
)
