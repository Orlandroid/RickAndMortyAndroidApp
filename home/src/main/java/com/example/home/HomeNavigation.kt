package com.example.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.core.navigation.AppNavigationRoutes
import com.example.home.screens.home.HomeRote

fun NavGraphBuilder.homeNavigationGraph(
    navController: NavHostController
) {
    navigation<AppNavigationRoutes.HomeRouteNavigation>(
        startDestination = AppNavigationRoutes.HomeRoute
    ) {
        composable<AppNavigationRoutes.HomeRoute> {
            HomeRote(navController)
        }
    }
}