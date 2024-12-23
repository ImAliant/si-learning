package fr.diamant.silearning.ui.settings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fr.diamant.silearning.viewmodel.settings.SettingsViewModel

@Composable
fun SettingsScreen(navController: NavController, paddingValues: PaddingValues, model: SettingsViewModel = viewModel()) {
    Text(text = "Settings Screen")
}