package fr.diamant.silearning.ui.game

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fr.diamant.silearning.R
import fr.diamant.silearning.viewmodel.game.GameViewModel
import java.util.logging.Logger

@Composable
fun GameScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    categoryId: Int,
    model: GameViewModel = viewModel()
) {
    //val userAnswer by model.userAnswer
    //val currentQuestion = remember { model.currentQuestion }
    //val context = LocalContext.current

    /*Column {
        // Show the current question
        Card {
            Text(text = currentQuestion.question)
        }

        // Textfield for the user to input their answer
        OutlinedTextField(
            value = userAnswer,
            onValueChange = model::updateUserAnswer,
            label = { Text(text = context.getString(R.string.answer_tf)) }
        )
    }*/

    model.initializeGame(categoryId)

    Text(text = model.currentQuestion?.question ?: "No question found")
}

/*@Composable
private fun ShowQuestions(model: GameViewModel, categoryId: Int) {
    model.initializeGame(categoryId)

    val questions = remember { model.questions }

    LazyColumn {
        itemsIndexed(questions) { _, question ->
            Text(text = question.question)
        }
    }
}*/
