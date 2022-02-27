package com.example.paggingexample.data.models

data class Character(
    val id:Int,
    val name: String,
    val status:String,
    val species:String,
    val type:String,
    val gender:String,
    val origin: Origin,
    val location: Location,
    val image:String,
    val episode:List<String>,
    val url:String,
)