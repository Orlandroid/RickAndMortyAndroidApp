package com.rickandmortyorlando.orlando.app_navigation

import kotlinx.serialization.Serializable


sealed class AppNavigationRoutes {
    @Serializable
    data object HomeRoute

    @Serializable
    data object CharactersRoute

    @Serializable
    data class CharactersDetailRoute(val id: Int)

    @Serializable
    data object SearchCharactersRoute

    @Serializable
    data object EpisodesRoute

    @Serializable
    data class EpisodesDetailRoute(val id: Int)

    @Serializable
    data class ManyEpisodesRoute(val idsEpisodes: String)

    @Serializable
    data object LocationsRoute

    @Serializable
    data class LocationDetailRoute(val id: Int)


    @Serializable
    data object SettingsRoute

    @Serializable
    data object DialogRoute
}