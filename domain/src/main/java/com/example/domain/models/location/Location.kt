package com.example.domain.models.location

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

data class Location(
    val id: Int,
    val name: String,
    val url: String,
    val dimension: String,
    val created: String,
    val type: String
) {
    @SuppressLint("SimpleDateFormat")
    fun getCreatedFormat(): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        return dateFormat.format(created).toString()
    }
}

fun Location.getPairInfoLocation() = listOf(
    Pair("Location Name", name),
    Pair("Type", type),
    Pair("Dimension", dimension),
    Pair("Create", created),
)
