package fr.diamant.silearning.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import fr.diamant.silearning.navigation.route.GAME
import fr.diamant.silearning.navigation.route.HOME
import fr.diamant.silearning.navigation.route.SETTINGS
import fr.diamant.silearning.ui.game.GameScreen
import fr.diamant.silearning.ui.home.HomeScreen
import fr.diamant.silearning.ui.settings.SettingsScreen

@Composable
fun NavigationGraph(navController: NavHostController, padding: PaddingValues, snackbarHostState: SnackbarHostState, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = NavigationDestinations.Home.route,
        modifier = modifier
    ) {
        composable(NavigationDestinations.Home.route) {
            HomeScreen(navController, padding, snackbarHostState)
        }
        composable(NavigationDestinations.Settings.route) {
            SettingsScreen(navController, padding)
        }

        composable(
            route = NavigationDestinations.Game.route + "/{categoryId}",
            arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
        ) {
            GameScreen(navController, padding, snackbarHostState, it.arguments!!.getInt("categoryId"))
        }
    }
}

sealed class NavigationDestinations(val route: String) {
    data object Home : NavigationDestinations(HOME)
    data object Settings : NavigationDestinations(SETTINGS)
    data object Game : NavigationDestinations(GAME)
}