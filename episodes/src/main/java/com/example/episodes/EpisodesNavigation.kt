package com.example.episodes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.core.navigation.AppNavigationRoutes
import com.example.episodes.screens.episode_detail.EpisodesDetailRoute
import com.example.episodes.screens.episodes.EpisodesRoute
import com.example.episodes.screens.many_episodes.ManyEpisodesRoute


fun NavGraphBuilder.episodesNavigationGraph(
    navController: NavHostController
) {
    navigation<AppNavigationRoutes.EpisodesNavigationRoute>(
        startDestination = AppNavigationRoutes.EpisodesRoute
    ) {
        composable<AppNavigationRoutes.EpisodesRoute> {
            EpisodesRoute(navController)
        }
        composable<AppNavigationRoutes.ManyEpisodesRoute> {
            val args = it.toRoute<AppNavigationRoutes.ManyEpisodesRoute>()
            ManyEpisodesRoute(navController = navController, idsEpisodes = args.idsEpisodes)
        }
        composable<AppNavigationRoutes.EpisodesDetailRoute> {
            val args = it.toRoute<AppNavigationRoutes.EpisodesDetailRoute>()
            EpisodesDetailRoute(
                navController = navController,
                episodesId = args.id
            )
        }
    }
}