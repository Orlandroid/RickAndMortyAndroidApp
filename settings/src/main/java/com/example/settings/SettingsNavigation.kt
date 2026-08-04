package com.example.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.core.navigation.AppNavigationRoutes
import com.example.settings.screens.settings.SettingsRoute

fun NavGraphBuilder.settingsNavigationGraph(
    navController: NavHostController
) {
    navigation<AppNavigationRoutes.SettingsRoute>(
        startDestination = AppNavigationRoutes.SettingsRoute
    ) {
        composable<AppNavigationRoutes.SettingsRoute> {
            SettingsRoute(navController)
        }
    }
}