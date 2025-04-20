package com.rickandmortyorlando.orlando.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


sealed class HomeEvents {
    data object ClickOnCharacters : HomeEvents()
    data object ClickOnEpisodes : HomeEvents()
    data object ClickOnLocations : HomeEvents()
    data object ClickOnSettings : HomeEvents()
}

sealed class HomeEffects {
    data object NavigateToCharacters : HomeEffects()
    data object NavigateToEpisodes : HomeEffects()
    data object NavigateToLocations : HomeEffects()
    data object NavigateToSettings : HomeEffects()
}

class HomeViewModel : ViewModel() {

    private val _effects = Channel<HomeEffects>()

    val effects = _effects.receiveAsFlow()

    fun onEvents(event: HomeEvents) {
        when (event) {
            HomeEvents.ClickOnCharacters -> {
                senEffect(HomeEffects.NavigateToCharacters)
            }

            HomeEvents.ClickOnEpisodes -> {
                senEffect(HomeEffects.NavigateToEpisodes)
            }

            HomeEvents.ClickOnLocations -> {
                senEffect(HomeEffects.NavigateToLocations)
            }

            HomeEvents.ClickOnSettings -> {
                senEffect(HomeEffects.NavigateToSettings)
            }
        }
    }

    private fun senEffect(effect: HomeEffects) {
        viewModelScope.launch {
            _effects.send(effect)
        }
    }
}