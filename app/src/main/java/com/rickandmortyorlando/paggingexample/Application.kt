package com.rickandmortyorlando.paggingexample

import dagger.hilt.android.HiltAndroidApp
import android.app.Application
import com.rickandmortyorlando.paggingexample.data.preferences.RickAndMortyPreferences
import com.rickandmortyorlando.paggingexample.utils.ThemeUtils
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
