package com.rickandmortyorlando.orlando.app_navigation


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.paging.compose.collectAsLazyPagingItems
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.components.ToolbarConfiguration
import com.rickandmortyorlando.orlando.features.base.BaseComposeScreen
import com.rickandmortyorlando.orlando.features.characters.CharacterViewModel
import com.rickandmortyorlando.orlando.features.characters.CharactersScreen
import com.rickandmortyorlando.orlando.features.characters_detail.CharacterDetailScreen
import com.rickandmortyorlando.orlando.features.characters_detail.CharacterDetailViewModel
import com.rickandmortyorlando.orlando.features.episode_detail.EpisodeDetailScreen
import com.rickandmortyorlando.orlando.features.episode_detail.EpisodeDetailViewModel
import com.rickandmortyorlando.orlando.features.episodes.EpisodesScreen
import com.rickandmortyorlando.orlando.features.episodes.EpisodesViewModel
import com.rickandmortyorlando.orlando.features.extensions.openYoutubeApp
import com.rickandmortyorlando.orlando.features.home.HomeEffects
import com.rickandmortyorlando.orlando.features.home.HomeScreen
import com.rickandmortyorlando.orlando.features.home.HomeViewModel
import com.rickandmortyorlando.orlando.features.location_detail.LocationDetailScreen
import com.rickandmortyorlando.orlando.features.location_detail.LocationDetailViewModel
import com.rickandmortyorlando.orlando.features.locations.LocationScreen
import com.rickandmortyorlando.orlando.features.locations.LocationsViewModel
import com.rickandmortyorlando.orlando.features.many_episodes.ManyEpisodesScreen
import com.rickandmortyorlando.orlando.features.many_episodes.ManyEpisodesViewModel
import com.rickandmortyorlando.orlando.features.search.SearchCharacterScreen
import com.rickandmortyorlando.orlando.features.search.SearchCharactersViewModel
import com.rickandmortyorlando.orlando.features.settings.SettingScreen
import com.rickandmortyorlando.orlando.features.settings.SettingsViewModel
import kotlinx.coroutines.flow.collectLatest


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppNavigationRoutes.HomeRoute
    ) {
        composable<AppNavigationRoutes.HomeRoute> {
            val viewModel: HomeViewModel = hiltViewModel()
            LaunchedEffect(viewModel) {
                viewModel.effects.collectLatest {
                    when (it) {
                        HomeEffects.NavigateToCharacters -> {
                            navController.navigate(AppNavigationRoutes.CharactersRoute)
                        }

                        HomeEffects.NavigateToEpisodes -> {
                            navController.navigate(AppNavigationRoutes.EpisodesRoute)
                        }

                        HomeEffects.NavigateToLocations -> {
                            navController.navigate(AppNavigationRoutes.LocationsRoute)
                        }

                        HomeEffects.NavigateToSettings -> {
                            navController.navigate(AppNavigationRoutes.SettingsRoute)
                        }
                    }
                }
            }
            HomeScreen(onEvents = viewModel::onEvents)
        }
        composable<AppNavigationRoutes.CharactersRoute> {
            val viewModel: CharacterViewModel = hiltViewModel()
            val characters = viewModel.characters.collectAsLazyPagingItems()
            BaseComposeScreen(
                toolbarConfiguration = ToolbarConfiguration(
                    title = stringResource(R.string.characters),
                    clickOnBackButton = { navController.navigateUp() },
                    actions = {
                        IconButton(
                            onClick = {
                                navController.navigate(AppNavigationRoutes.SearchCharactersRoute)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Search",
                                tint = Color.White
                            )
                        }
                    }
                )
            ) {
                CharactersScreen(
                    characters = characters,
                    clickOnItem = { characterId, characterName ->
                        navController.navigate(
                            AppNavigationRoutes.CharactersDetailRoute(
                                id = characterId,
                                name = characterName
                            )
                        )
                    }
                )
            }
        }
        composable<AppNavigationRoutes.CharactersDetailRoute> {
            val args = it.toRoute<AppNavigationRoutes.CharactersDetailRoute>()
            val viewModel: CharacterDetailViewModel = hiltViewModel()
            LaunchedEffect(Unit) {
                viewModel.getCharacterDetailInfo(args.id)
            }
            val state by viewModel.state.collectAsStateWithLifecycle()
            CharacterDetailScreen(
                viewState = state,
                clickOnCharacter = { id, name ->
                    navController.navigate(
                        AppNavigationRoutes.CharactersDetailRoute(
                            id = id,
                            name = name
                        )
                    )
                },
                clickOnNumberOfEpisodes = { idsOfEpisodes ->
                    navController.navigate(AppNavigationRoutes.ManyEpisodesRoute(idsEpisodes = idsOfEpisodes))
                },
                onBack = { navController.navigateUp() }
            )
        }
        composable<AppNavigationRoutes.SearchCharactersRoute> {
            val viewModel: SearchCharactersViewModel = hiltViewModel()
            val characters = viewModel.getCharactersSearchPagingSource.collectAsLazyPagingItems()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()
            SearchCharacterScreen(
                onBack = { navController.navigateUp() },
                characters = characters,
                events = viewModel::handleEvents,
                uiState = uiState.value,
                clickOnCharacter = { id, name ->
                    navController.navigate(
                        AppNavigationRoutes.CharactersDetailRoute(
                            id = id,
                            name = name
                        )
                    )
                }
            )
        }
        composable<AppNavigationRoutes.EpisodesRoute> {
            val viewModel: EpisodesViewModel = hiltViewModel()
            val episodes = viewModel.episodes.collectAsLazyPagingItems()
            EpisodesScreen(
                episodes = episodes,
                clickOnItem = {
                    navController.navigate(AppNavigationRoutes.EpisodesDetailRoute(it))
                },
                clickOnBackButton = { navController.navigateUp() }
            )
        }
        composable<AppNavigationRoutes.ManyEpisodesRoute> {
            val args = it.toRoute<AppNavigationRoutes.ManyEpisodesRoute>()
            val viewModel: ManyEpisodesViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()
            LaunchedEffect(Unit) {
                viewModel.getEpisodes(args.idsEpisodes)
            }
            ManyEpisodesScreen(
                viewState = state,
                onNavigateBack = { navController.navigateUp() }
            )
        }
        composable<AppNavigationRoutes.EpisodesDetailRoute> {
            val context = LocalContext.current
            val args = it.toRoute<AppNavigationRoutes.EpisodesDetailRoute>()
            val viewModel: EpisodeDetailViewModel = hiltViewModel()
            LaunchedEffect(Unit) {
                viewModel.getEpisodeDetail(args.id.toString())
            }
            val state = viewModel.state.collectAsStateWithLifecycle()
            EpisodeDetailScreen(
                viewState = state.value,
                clickOnCharacter = { characterId, characterName ->
                    navController.navigate(
                        AppNavigationRoutes.CharactersDetailRoute(
                            id = characterId,
                            name = characterName
                        )
                    )
                },
                clickOnWatch = { episodeName ->
                    context.openYoutubeApp(episodeName)
                }
            )
        }
        composable<AppNavigationRoutes.LocationsRoute> {
            val viewModel: LocationsViewModel = hiltViewModel()
            val locations = viewModel.locations.collectAsLazyPagingItems()
            LocationScreen(
                locations = locations,
                clickOnItem = {
                    navController.navigate(AppNavigationRoutes.LocationDetailRoute(it))
                },
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable<AppNavigationRoutes.LocationDetailRoute> {
            val args = it.toRoute<AppNavigationRoutes.LocationDetailRoute>()
            val locationDetailViewModel: LocationDetailViewModel = hiltViewModel()
            LaunchedEffect(Unit) {
                locationDetailViewModel.getLocationDetail(locationId = args.id)
            }
            val state = locationDetailViewModel.state.collectAsState()
            LocationDetailScreen(
                viewState = state.value,
                clickOnCharacter = { id, name ->
                    navController.navigate(
                        AppNavigationRoutes.CharactersDetailRoute(
                            id = id,
                            name = name
                        )
                    )
                },
                clickOnBack = {
                    navController.navigateUp()
                }
            )
        }
        composable<AppNavigationRoutes.SettingsRoute> {
            val viewModel: SettingsViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            SettingScreen(
                uiState = uiState,
                onEvents = viewModel::handleEvents,
                onBack = { navController.navigateUp() }
            )
        }
    }
}