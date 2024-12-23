package fr.diamant.silearning

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = NavigationDestinations.Home.route,
        modifier = modifier
    ) {
        composable(NavigationDestinations.Home.route) {
            //HomeScreen()
        }
        composable(NavigationDestinations.Settings.route) {
            //SettingsScreen()
        }
    }
}

sealed class NavigationDestinations(val route: String) {
    data object Home : NavigationDestinations("home")
    data object Settings : NavigationDestinations("settings")
}