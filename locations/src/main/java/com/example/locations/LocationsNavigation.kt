package com.example.locations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.core.navigation.AppNavigationRoutes
import com.example.locations.screens.location_detail.LocationDetailRoute
import com.example.locations.screens.locations.LocationRoute


fun NavGraphBuilder.locationsNavigationGraph(
    navController: NavHostController
) {
    navigation<AppNavigationRoutes.LocationsRoute>(
        startDestination = AppNavigationRoutes.LocationsRoute
    ) {
        composable<AppNavigationRoutes.LocationsRoute> {
            LocationRoute(navController)
        }
        composable<AppNavigationRoutes.LocationDetailRoute> {
            val args = it.toRoute<AppNavigationRoutes.LocationDetailRoute>()
            LocationDetailRoute(navController = navController, locationId = args.id)
        }
    }
}