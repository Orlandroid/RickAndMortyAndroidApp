package com.rickandmortyorlando.presentation

import dagger.hilt.android.HiltAndroidApp
import android.app.Application
import com.example.data.preferences.RickAndMortyPreferences
import com.rickandmortyorlando.presentation.utils.ThemeUtils
import javax.inject.Inject


@HiltAndroidApp
class Application : Application() {

    @Inject
    lateinit var rickAndMortyPreferences: RickAndMortyPreferences
    override fun onCreate() {
        super.onCreate()
        ThemeUtils.themeUtils.setNightMode(rickAndMortyPreferences.getIsNightMode())
    }
}
