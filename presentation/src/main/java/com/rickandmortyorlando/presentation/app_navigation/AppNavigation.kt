package com.rickandmortyorlando.presentation.app_navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.characters.charactersNavigationGraph
import com.example.core.navigation.AppNavigationRoutes
import com.example.episodes.episodesNavigationGraph
import com.example.home.homeNavigationGraph
import com.example.locations.locationsNavigationGraph
import com.example.settings.settingsNavigationGraph


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppNavigationRoutes.HomeRouteNavigation
    ) {
        homeNavigationGraph(navController = navController)
        charactersNavigationGraph(navController = navController)
        episodesNavigationGraph(navController = navController)
        locationsNavigationGraph(navController = navController)
        settingsNavigationGraph(navController = navController)
    }
}