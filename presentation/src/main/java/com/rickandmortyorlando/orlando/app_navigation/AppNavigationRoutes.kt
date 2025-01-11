package com.rickandmortyorlando.orlando.app_navigation

import kotlinx.serialization.Serializable


sealed class AppNavigationRoutes {
    @Serializable
    data object HomeScreenRoute

    @Serializable
    data object CharactersScreenRoute

    @Serializable
    data object EpisodesScreenRoute

    @Serializable
    data object LocationScreenRoute
}