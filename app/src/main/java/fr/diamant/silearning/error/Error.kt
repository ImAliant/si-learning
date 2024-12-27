package fr.diamant.silearning.error

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import fr.diamant.silearning.R

@Composable
fun ErrorHandler(error: ErrorType, snackbarHostState: SnackbarHostState) {
    val context = LocalContext.current

    LaunchedEffect(error) {
        checkError(error, context, snackbarHostState)
    }
}

private suspend fun checkError(error: ErrorType, context: Context, snackbarHostState: SnackbarHostState) {
    when (error) {
        ErrorType.NO_CATEGORY_SELECTED -> {
            snackbarHostState.showSnackbar(context.getString(R.string.no_category_selected))
        }
        ErrorType.NO_QUESTION_FOUND_FOR_CATEGORY -> {
            snackbarHostState.showSnackbar(context.getString(R.string.no_question_found_for_category))
        }
        else -> {
            // Do nothing
        }
    }
}