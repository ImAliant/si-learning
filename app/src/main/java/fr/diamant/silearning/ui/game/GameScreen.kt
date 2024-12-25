package fr.diamant.silearning.ui.game

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fr.diamant.silearning.viewmodel.game.GameViewModel

@Composable
fun GameScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    categoryId: Int?,
    model: GameViewModel = viewModel()
) {
    ShowQuestions(model, categoryId ?: -1)
}

@Composable
private fun ShowQuestions(model: GameViewModel, categoryId: Int) {
    model.getQuestions(categoryId)

    val questions = model.questions

    LazyColumn {
        itemsIndexed(questions) { _, question ->
            Text(text = question.question)
        }
    }
}