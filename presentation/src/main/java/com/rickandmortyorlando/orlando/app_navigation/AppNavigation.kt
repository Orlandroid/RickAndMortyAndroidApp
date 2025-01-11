package com.rickandmortyorlando.orlando.app_navigation


import androidx.compose.runtime.Composable
import androidx.fragment.compose.AndroidFragment
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rickandmortyorlando.orlando.features.characters.CharacterFragment
import com.rickandmortyorlando.orlando.features.episodes.EpisodesFragment
import com.rickandmortyorlando.orlando.features.home.HomeFragment
import com.rickandmortyorlando.orlando.features.locations.LocationsFragment


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppNavigationRoutes.HomeScreenRoute
    ) {
        composable<AppNavigationRoutes.HomeScreenRoute> {
            AndroidFragment<HomeFragment>()
            composable<AppNavigationRoutes.CharactersScreenRoute> {
                AndroidFragment<CharacterFragment> {

                }
            }
            composable<AppNavigationRoutes.EpisodesScreenRoute> {
                AndroidFragment<EpisodesFragment>()
            }
            composable<AppNavigationRoutes.LocationScreenRoute> {
                AndroidFragment<LocationsFragment>()
            }
        }
    }
}