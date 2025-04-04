package fr.diamant.silearning.navigation

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
import fr.diamant.silearning.ui.game.GameScreen
import fr.diamant.silearning.ui.home.HomeScreen

@Composable
fun NavigationGraph(navController: NavHostController, snackbarHostState: SnackbarHostState, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = NavigationDestinations.Home.route,
        modifier = modifier
    ) {
        composable(NavigationDestinations.Home.route) {
            HomeScreen(navController, snackbarHostState)
        }

        composable(
            route = NavigationDestinations.Game.route + "/{categoryId}",
            arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
        ) {
            GameScreen(navController, snackbarHostState, it.arguments!!.getInt("categoryId"))
        }
    }
}

sealed class NavigationDestinations(val route: String) {
    data object Home : NavigationDestinations(HOME)
    data object Game : NavigationDestinations(GAME)
}