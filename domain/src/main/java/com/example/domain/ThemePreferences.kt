package com.example.domain

interface ThemePreferences {

    fun saveIsNightMode(isNightMode: Boolean?)

    fun getIsNightMode(): Boolean
}