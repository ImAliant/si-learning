@file:OptIn(ExperimentalMaterial3Api::class)

package fr.diamant.silearning

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.rememberNavController
import fr.diamant.silearning.ui.theme.SILearningTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SILearningTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(topBar = { TopBar() },
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        NavigationGraph(navController, Modifier.padding(innerPadding))
    }
}

@Composable
fun TopBar() = CenterAlignedTopAppBar(title = {
    Text(
        text = LocalContext.current.getString(R.string.app_name),
        style = MaterialTheme.typography.displayMedium
    )
})

@Composable
fun BottomBar(navController: NavController) {
    val items = listOf(
        NavigationDestinations.Home,
        NavigationDestinations.Settings
    )

    NavigationBar {
        items.forEach { destination ->
            NavigationBarItem(
                selected = navController.currentBackStackEntry?.destination?.hierarchy?.any { it.route == destination.route } == true,
                onClick = { navController.navigate(destination.route) },
                label = { Text(text = destination.route.replaceFirstChar { it.uppercase() }) },
                icon = { Icon(Icons.Default.Home, contentDescription = destination.route) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    SILearningTheme {
        MainScreen()
    }
}