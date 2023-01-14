package com.example.paggingexample.data.models.remote.character

data class Info(
    val count:Int,
    val pages:Int,
    val next:String,
    val prev:String
)
