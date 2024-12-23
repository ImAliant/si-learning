package fr.diamant.silearning

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
            HomeScreen(navController, padding)
        }
        composable(NavigationDestinations.Settings.route) {
            SettingsScreen(navController, padding)
        }
    }
}

sealed class NavigationDestinations(val route: String) {
    data object Home : NavigationDestinations("home")
    data object Settings : NavigationDestinations("settings")
}