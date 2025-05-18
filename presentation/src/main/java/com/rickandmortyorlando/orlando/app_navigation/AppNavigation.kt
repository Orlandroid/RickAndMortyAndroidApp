package com.rickandmortyorlando.orlando.app_navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.rickandmortyorlando.orlando.features.characters.CharacterRoute
import com.rickandmortyorlando.orlando.features.characters_detail.CharacterDetailRoute
import com.rickandmortyorlando.orlando.features.episode_detail.EpisodesDetailRoute
import com.rickandmortyorlando.orlando.features.episodes.EpisodesRoute
import com.rickandmortyorlando.orlando.features.home.HomeRote
import com.rickandmortyorlando.orlando.features.location_detail.LocationDetailRoute
import com.rickandmortyorlando.orlando.features.locations.LocationRoute
import com.rickandmortyorlando.orlando.features.many_episodes.ManyEpisodesRoute
import com.rickandmortyorlando.orlando.features.search.SearchCharacterRoute
import com.rickandmortyorlando.orlando.features.settings.SettingsRoute


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppNavigationRoutes.HomeRoute
    ) {
        composable<AppNavigationRoutes.HomeRoute> {
            HomeRote(navController)
        }
        composable<AppNavigationRoutes.CharactersRoute> {
            CharacterRoute(navController)
        }
        composable<AppNavigationRoutes.CharactersDetailRoute> {
            val args = it.toRoute<AppNavigationRoutes.CharactersDetailRoute>()
            CharacterDetailRoute(navController = navController, idCharacter = args.id)
        }
        composable<AppNavigationRoutes.SearchCharactersRoute> {
            SearchCharacterRoute(navController)
        }
        composable<AppNavigationRoutes.EpisodesRoute> {
            EpisodesRoute(navController)
        }
        composable<AppNavigationRoutes.ManyEpisodesRoute> {
            val args = it.toRoute<AppNavigationRoutes.ManyEpisodesRoute>()
            ManyEpisodesRoute(
                navController = navController,
                idsEpisodes = args.idsEpisodes
            )
        }
        composable<AppNavigationRoutes.EpisodesDetailRoute> {
            val args = it.toRoute<AppNavigationRoutes.EpisodesDetailRoute>()
            EpisodesDetailRoute(navController = navController, episodesId = args.id.toString())
        }
        composable<AppNavigationRoutes.LocationsRoute> {
            LocationRoute(navController)
        }
        composable<AppNavigationRoutes.LocationDetailRoute> {
            val args = it.toRoute<AppNavigationRoutes.LocationDetailRoute>()
            LocationDetailRoute(navController = navController, locationId = args.id)
        }
        composable<AppNavigationRoutes.SettingsRoute> {
            SettingsRoute(navController)
        }
    }
}