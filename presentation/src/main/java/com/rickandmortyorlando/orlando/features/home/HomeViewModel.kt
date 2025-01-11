package com.rickandmortyorlando.orlando.features.home

import androidx.lifecycle.ViewModel


sealed class HomeEvents {
    data object ClickOnCharacters : HomeEvents()
    data object ClickOnEpisodes : HomeEvents()
    data object ClickOnLocations : HomeEvents()
}

class HomeViewModel : ViewModel() {

    fun onEvents(event: HomeEvents) {
        when (event) {
            HomeEvents.ClickOnCharacters -> {

            }

            HomeEvents.ClickOnEpisodes -> {

            }

            HomeEvents.ClickOnLocations -> {

            }
        }
    }
}