package fr.diamant.silearning.ui.game

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fr.diamant.silearning.R
import fr.diamant.silearning.data.entity.Question
import fr.diamant.silearning.error.SnackbarHandler
import fr.diamant.silearning.viewmodel.game.GameViewModel

@Composable
fun GameScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    snackbarHostState: SnackbarHostState,
    categoryId: Int,
    model: GameViewModel = viewModel()
) {
    val current by remember { model.currentQuestion }
    val userAnswer by remember { model.userAnswer }
    val context = LocalContext.current
    val snackbarMessage by remember { model.snackbarMessage }

    LaunchedEffect(categoryId) {
        model.initializeGame(categoryId)
    }

    SnackbarHandler(snackbarMessage, snackbarHostState)

    GameUI(
        navController = navController,
        paddingValues = paddingValues,
        current = current,
        userAnswer = userAnswer,
        context = context,
        model = model
    )
}

@Composable
private fun GameUI(
    navController: NavController,
    paddingValues: PaddingValues,
    current: Question?,
    userAnswer: String,
    context: Context,
    model: GameViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Information(model)

        Spacer(modifier = Modifier.height(16.dp))

        QuestionText(question = current?.question, context = context)

        AnswerInput(
            userAnswer = userAnswer,
            context = context,
            onAnswerChange = model::updateUserAnswer
        )

        ActionButtons(
            onCheckClick = { model.checkAnswer(navController) },
            onNextClick = { model.nextQuestion(navController) },
            checkEnabler = { userAnswer.isNotEmpty() },
            checkText = context.getString(R.string.check_btn),
            nextText = context.getString(R.string.next_btn)
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun Information(model: GameViewModel) {
    val currentIndex by remember { model.currentQI }
    val size by remember { model.size }

    Text(
        text = "$currentIndex/$size",
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Composable
private fun QuestionText(question: String?, context: Context) {
    Text(
        text = question ?: context.getString(R.string.no_question_found),
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Composable
private fun AnswerInput(userAnswer: String, context: Context, onAnswerChange: (String) -> Unit) {
    OutlinedTextField(
        value = userAnswer,
        onValueChange = onAnswerChange,
        label = { Text(text = context.getString(R.string.answer_tf)) },
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Composable
private fun ActionButtons(
    onCheckClick: () -> Unit,
    onNextClick: () -> Unit,
    checkEnabler: () -> Boolean = { true },
    nextEnabler: () -> Boolean = { true },
    checkText: String,
    nextText: String
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        Button(
            onClick = onCheckClick,
            enabled = checkEnabler()
        ) {
            Text(text = checkText)
        }

        Button(
            onClick = onNextClick,
            enabled = nextEnabler()
        ) {
            Text(text = nextText)
        }
    }
}
