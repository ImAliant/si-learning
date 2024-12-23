package fr.diamant.silearning.ui.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fr.diamant.silearning.viewmodel.home.HomeViewModel

@Composable
fun HomeScreen(navController: NavController, paddingValues: PaddingValues, model: HomeViewModel = viewModel()) {
    //val context = LocalContext.current

    Text(text = "Home Screen")
}