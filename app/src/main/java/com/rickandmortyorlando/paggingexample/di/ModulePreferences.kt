package com.rickandmortyorlando.paggingexample.di

import android.content.Context
import android.content.SharedPreferences
import com.rickandmortyorlando.paggingexample.data.preferences.PreferencesManager
import com.rickandmortyorlando.paggingexample.data.preferences.RickAndMortyPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModulePreferences {

    private const val SHARE_PREFERENCES = "RickAndMorty"

    @Singleton
    @Provides
    fun provideSharePreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(SHARE_PREFERENCES, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun providePreferencesManager(sharedPreferences: SharedPreferences) =
        PreferencesManager(sharedPreferences)

    @Singleton
    @Provides
    fun provideLoginPreferences(sharedPreferences: SharedPreferences) =
        RickAndMortyPreferences(sharedPreferences)


}