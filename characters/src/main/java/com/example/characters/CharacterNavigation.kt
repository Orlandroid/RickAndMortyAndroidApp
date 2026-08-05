package com.example.characters

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.characters.screens.characters.CharacterRoute
import com.example.characters.screens.characters_detail.CharacterDetailRoute
import com.example.characters.screens.search.SearchCharacterRoute
import com.example.core.navigation.AppNavigationRoutes


fun NavGraphBuilder.charactersNavigationGraph(
    navController: NavHostController
) {
    navigation<AppNavigationRoutes.CharactersNavigationRoute>(
        startDestination = AppNavigationRoutes.CharactersRoute
    ) {
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
    }
}