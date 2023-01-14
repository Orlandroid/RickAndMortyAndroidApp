package com.example.paggingexample.data.models.remote.episode

data class Info(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: Any
)