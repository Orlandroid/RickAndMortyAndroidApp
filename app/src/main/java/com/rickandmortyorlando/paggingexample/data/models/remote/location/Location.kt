package com.rickandmortyorlando.paggingexample.data.models.remote.location

data class SingleLocation(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String
)