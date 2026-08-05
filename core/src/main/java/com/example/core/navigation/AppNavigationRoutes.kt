package com.example.core.navigation

import kotlinx.serialization.Serializable


sealed class AppNavigationRoutes {

    @Serializable
    data object HomeRouteNavigation : AppNavigationRoutes()

    @Serializable
    data object HomeRoute : AppNavigationRoutes()


    @Serializable
    data object CharactersNavigationRoute : AppNavigationRoutes()

    @Serializable
    data object CharactersRoute : AppNavigationRoutes()

    @Serializable
    data class CharactersDetailRoute(val id: Int)

    @Serializable
    data object SearchCharactersRoute : AppNavigationRoutes()


    @Serializable
    data object EpisodesNavigationRoute : AppNavigationRoutes()

    @Serializable
    data object EpisodesRoute : AppNavigationRoutes()

    @Serializable
    data class EpisodesDetailRoute(val id: Int) : AppNavigationRoutes()

    @Serializable
    data class ManyEpisodesRoute(val idsEpisodes: String) : AppNavigationRoutes()

    @Serializable
    data object LocationsNavigationRoute : AppNavigationRoutes()

    @Serializable
    data object LocationsRoute : AppNavigationRoutes()

    @Serializable
    data class LocationDetailRoute(val id: Int) : AppNavigationRoutes()


    @Serializable
    data object SettingsNavigationRoute : AppNavigationRoutes()

    @Serializable
    data object SettingsRoute : AppNavigationRoutes()
}