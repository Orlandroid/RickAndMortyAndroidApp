package com.rickandmortyorlando.orlando

import dagger.hilt.android.HiltAndroidApp
import android.app.Application
import com.example.data.preferences.RickAndMortyPreferences
import com.rickandmortyorlando.orlando.utils.ThemeUtils
import timber.log.Timber
import javax.inject.Inject


@HiltAndroidApp
class Application : Application() {

    @Inject
    lateinit var rickAndMortyPreferences: RickAndMortyPreferences
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        ThemeUtils.themeUtils.setNightMode(rickAndMortyPreferences.getIsNightMode())
    }
}
