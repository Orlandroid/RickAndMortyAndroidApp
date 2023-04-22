package com.rickandmortyorlando.orlando

import dagger.hilt.android.HiltAndroidApp
import android.app.Application
import com.rickandmortyorlando.orlando.data.preferences.RickAndMortyPreferences
import com.rickandmortyorlando.orlando.utils.ThemeUtils
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
