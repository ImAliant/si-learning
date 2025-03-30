package fr.diamant.silearning.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fr.diamant.silearning.R
import fr.diamant.silearning.data.entity.Category
import fr.diamant.silearning.data.entity.RANDOM_ID
import fr.diamant.silearning.message.SnackbarHandler
import fr.diamant.silearning.viewmodel.home.HomeViewModel

private const val GRID_COLUMNS = 2

@Composable
fun HomeScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    model: HomeViewModel = viewModel()
) {
    val snackbarMessage by remember { model.snackbarMessage }

    SnackbarHandler(snackbarMessage, snackbarHostState)

    HomeUI(navController, model)
}

@Composable
fun HomeUI(navController: NavController, model: HomeViewModel) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        ShowCategories(navController, model)
    }
}

@Composable
private fun ShowCategories(navController: NavController, model: HomeViewModel) {
    val categories = model.categories

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        if (categories.isNotEmpty()) {
            // First category takes full width
            item {
                FullWidthCategory(categories[RANDOM_ID-1], navController, model)
            }

            // Remaining categories in a grid format
            items(categories.drop(1).chunked(GRID_COLUMNS)) { rowCategories ->
                CategoryRow(rowCategories, navController, model)
            }
        }
    }
}

@Composable
private fun FullWidthCategory(category: Category, navController: NavController, model: HomeViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp), // Adjust height as needed
        contentAlignment = Alignment.Center
    ) {
        ListCategory(RANDOM_ID-1, category, navController, model)
    }
}

@Composable
private fun CategoryRow(categories: List<Category>, navController: NavController, model: HomeViewModel) {
    Row(modifier = Modifier.fillMaxWidth()) {
        categories.forEach { category ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1.65f)
            ) {
                ListCategory(category.id, category, navController, model)
            }
        }
    }
}

@Composable
private fun ListCategory(index: Int, category: Category, navController: NavController, model: HomeViewModel) {
    val containerColor = getContainerColor(index)

    Card(
        onClick = { model.play(category, navController) },
        modifier = Modifier
            .padding(8.dp)
            .fillMaxHeight()
            .height(150.dp),
        colors = CardDefaults.cardColors(containerColor)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = category.name,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun getContainerColor(index: Int) = when {
    index%2 == 0 -> colorResource(id = R.color.purple_200)
    else -> colorResource(id = R.color.purple_500)
}