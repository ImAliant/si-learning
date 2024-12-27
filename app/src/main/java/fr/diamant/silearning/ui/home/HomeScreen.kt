package fr.diamant.silearning.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fr.diamant.silearning.R
import fr.diamant.silearning.data.entity.Category
import fr.diamant.silearning.error.SnackbarHandler
import fr.diamant.silearning.viewmodel.home.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    snackbarHostState: SnackbarHostState,
    model: HomeViewModel = viewModel()
) {
    val selected by remember { model.selectedCategories }
    val snackbarMessage by remember { model.snackbarMessage }

    SnackbarHandler(snackbarMessage, snackbarHostState)

    Column(
        modifier = Modifier.padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ShowCategories(model)

        Button(
            enabled = selected != null,
            onClick = { model.playSelected(navController) }
        ) {
            Text(text = LocalContext.current.getString(R.string.play))
        }
    }
}

@Composable
private fun ShowCategories(model: HomeViewModel) {
    val categories = model.categories

    LazyColumn(
        Modifier.fillMaxHeight(0.6f)
    ) {
        itemsIndexed(categories) { index, category ->
            ListCategory(index, category, model)
        }
    }
}

@Composable
private fun ListCategory(index: Int, category: Category, model: HomeViewModel) {
    val currentSelected = model.selectedCategories.value
    val containerColor = when {
        currentSelected?.id == category.id -> colorResource(id = R.color.purple_700)
        index%2 == 0 -> colorResource(id = R.color.purple_200)
        else -> colorResource(id = R.color.purple_500)
    }

    Card(
        onClick = { model.updateSelection(category) },
        modifier = Modifier.fillMaxHeight(),
        colors = CardDefaults.cardColors(containerColor)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = category.name, modifier = Modifier.padding(2.dp))
        }
    }
}