package fr.diamant.silearning.error

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import fr.diamant.silearning.R
import java.util.logging.Logger

@Composable
fun SnackbarHandler(message: MessageType, snackbarHostState: SnackbarHostState) {
    val context = LocalContext.current

    LaunchedEffect(message) {
        checkMessage(message, context, snackbarHostState)
    }
}

private suspend fun checkMessage(message: MessageType, context: Context, snackbarHostState: SnackbarHostState) {
    when (message) {
        MessageType.NO_CATEGORY_SELECTED -> {
            snackbarHostState.showSnackbar(context.getString(R.string.no_category_selected))
        }
        MessageType.NO_QUESTION_FOUND_FOR_CATEGORY -> {
            snackbarHostState.showSnackbar(context.getString(R.string.no_question_found_for_category))
        }
        else -> {
            // Do nothing
        }
    }
}