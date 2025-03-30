package fr.diamant.silearning.ui.game

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fr.diamant.silearning.R
import fr.diamant.silearning.data.entity.Question
import fr.diamant.silearning.message.SnackbarHandler
import fr.diamant.silearning.viewmodel.game.GameViewModel

@Composable
fun GameScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    snackbarHostState: SnackbarHostState,
    categoryId: Int,
    model: GameViewModel = viewModel()
) {
    val currentQ by remember { model.currentQuestion }
    val context = LocalContext.current
    val snackbarMessage by remember { model.snackbarMessage }

    InitializeGame(model, categoryId)
    SnackbarHandler(snackbarMessage, snackbarHostState)

    GameUI(
        navController = navController,
        paddingValues = paddingValues,
        currentQ = currentQ,
        context = context,
        model = model
    )
}

@Composable
private fun InitializeGame(model: GameViewModel, categoryId: Int) {
    LaunchedEffect(categoryId) {
        model.initializeGame(categoryId)
    }
}

@Composable
private fun GameUI(
    navController: NavController,
    paddingValues: PaddingValues,
    currentQ: Question?,
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

        QuestionText(question = currentQ?.question, context = context)

        ImageViewer(currentImage = currentQ?.image)

        AnswerViewer(
            model = model,
            context = context,
            currentAnswer = currentQ?.answer
        )

        ActionButtons(
            model = model,
            onPreviousClick = { model.moveToPreviousQuestion(navController) },
            onPrintClick = { model.printAnswer() },
            onNextClick = { model.moveToNextQuestion(navController) },
            onChangeClick = { model.changeStatusQuestion() },
            previousText = context.getString(R.string.previous_btn),
            checkText = context.getString(R.string.print_btn),
            nextText = context.getString(R.string.next_btn)
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun Information(model: GameViewModel) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        CurrentQuestion(model)
    }
}

@Composable
private fun CurrentQuestion(model: GameViewModel) {
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
private fun AnswerViewer(model: GameViewModel, context: Context, currentAnswer: String?) {
    val showAnwser by remember { model.showAnswer }

    if (showAnwser) {
        Text(
            text = currentAnswer ?: "",
            textAlign = TextAlign.Center,
            color = Color(ContextCompat.getColor(context, R.color.red)),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun ImageViewer(currentImage: Int?) {
    if (currentImage != null && currentImage != 0) {
        Image(
            painter = painterResource(id = currentImage),
            contentDescription = null,
            modifier = Modifier
                .height(300.dp)
                .width(300.dp)
        )
    }
}

@Composable
private fun ActionButtons(
    model: GameViewModel,
    onPreviousClick: () -> Unit,
    onPrintClick: () -> Unit,
    onNextClick: () -> Unit,
    onChangeClick: () -> Unit,
    previousText: String,
    checkText: String,
    nextText: String
) {
    val needHelp by remember { model.currentNeedHelp }

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        Button(
            onClick = onPreviousClick
        ) {
            Text(text = previousText)
        }

        Button(
            onClick = onPrintClick
        ) {
            Text(text = checkText)
        }

        Button(
            onClick = onNextClick
        ) {
            Text(text = nextText)
        }
    }

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        Button(
            onClick = onChangeClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (needHelp) Color.Red else Color.Green
            )
        ) {
            Text(text = stringResource(if (needHelp) R.string.change_btn else R.string.need_help_btn))
        }
    }
}
