package fr.diamant.silearning.ui.game

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import fr.diamant.silearning.ui.button.GameButton
import fr.diamant.silearning.viewmodel.game.GameViewModel

@Composable
fun GameScreen(
    navController: NavController,
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
    currentQ: Question?,
    context: Context,
    model: GameViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GameContent(
            currentQ = currentQ,
            modifier = Modifier.weight(1f),
            context = context,
            model = model
        )

        GameActions(
            navController = navController,
            currentQ = currentQ,
            context = context,
            model = model
        )
    }
}

@Composable
private fun GameContent(
    currentQ: Question?,
    modifier: Modifier = Modifier,
    context: Context,
    model: GameViewModel
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Information(model)

        Spacer(modifier = Modifier.height(16.dp))

        QuestionText(question = currentQ?.question, context = context)

        ImageViewer(currentImage = currentQ?.image)
    }
}

@Composable
private fun GameActions(
    navController: NavController,
    currentQ: Question?,
    context: Context,
    model: GameViewModel,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp), // Adds spacing from the bottom of the screen
        contentAlignment = Alignment.BottomCenter
    ) {
        AnswerViewer(
            model = model,
            context = context,
            currentAnswer = currentQ?.answer
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp), // Adds spacing from the bottom of the screen
        contentAlignment = Alignment.BottomCenter
    ) {
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

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp), // Adjust the spacing between rows
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            GameButton(previousText, onClick = onPreviousClick, modifier = Modifier.padding(8.dp))
            GameButton(nextText, onClick = onNextClick, modifier = Modifier.padding(8.dp))
        }

        GameButton(checkText, onClick = onPrintClick, Modifier.padding(top = 8.dp))

        GameButton(
            text = stringResource(if (needHelp) R.string.change_btn else R.string.need_help_btn),
            onClick = onChangeClick,
            modifier = Modifier.padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (needHelp) Color(0xFFBD3DEC) else Color(0xFFF9A6FF),
            )
        )
    }
}
