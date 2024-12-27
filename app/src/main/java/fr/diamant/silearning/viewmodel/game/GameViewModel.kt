package fr.diamant.silearning.viewmodel.game

import android.app.Application
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import fr.diamant.silearning.SILearningApplication
import fr.diamant.silearning.data.entity.Question
import fr.diamant.silearning.error.ErrorType
import fr.diamant.silearning.navigation.NavigationDestinations
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.logging.Logger

class GameViewModel(application: Application): AndroidViewModel(application) {
    private val _container = (application as SILearningApplication).container
    private var currentQuestionIndex = mutableIntStateOf(0)
    private var isGameStarted = mutableStateOf(false)
    private var questions = mutableStateListOf<Question>()

    var currentQI = mutableIntStateOf(1)
    var size = mutableIntStateOf(0)

    var currentQuestion = mutableStateOf<Question?>(null)
    var error = mutableStateOf(ErrorType.DEFAULT)
    var userAnswer = mutableStateOf("")

    fun initializeGame(categoryId: Int) {
        viewModelScope.launch {
            getQuestions(categoryId)

            if (questions.isNotEmpty() && !isGameStarted.value) {
                initCurrentQuestion()
                isGameStarted.value = true
            } else {
                error.value = ErrorType.NO_QUESTION_FOUND_FOR_CATEGORY
            }

            Logger.getLogger("GameViewModel").info("Game initialized")
            Logger.getLogger("GameViewModel").info("Current question: $currentQuestion")
            Logger.getLogger("GameViewModel").info("Questions size: ${questions.size}")
            Logger.getLogger("GameViewModel").info("Current question index: $currentQuestionIndex")
        }
    }

    private suspend fun getQuestions(categoryId: Int) {
        val fetchedQuestions = _container.Repository.getQuestionsByCategoryId(categoryId).first()

        questions.clear()
        questions.addAll(fetchedQuestions)
        size.intValue = questions.size
        shuffleQuestions()
    }

    private fun shuffleQuestions() {
        questions.shuffle()
    }

    private fun initCurrentQuestion() {
        if (questions.isNotEmpty()) {
            Logger.getLogger("GameViewModel").info("Current question index: $currentQuestionIndex")
            currentQuestion.value = questions[currentQuestionIndex.intValue]
        } else {
            Logger.getLogger("GameViewModel").warning("No question found for category")
            error.value = ErrorType.NO_QUESTION_FOUND_FOR_CATEGORY
        }
    }

    fun checkAnswer(navController: NavController) {
        if (currentQuestion.value?.answer == userAnswer.value) {
            Logger.getLogger("GameViewModel").info("Correct answer")
        } else {
            Logger.getLogger("GameViewModel").warning("Wrong answer")
        }
        nextQuestion(navController)
    }

    fun nextQuestion(navController: NavController) {
        currentQuestionIndex.intValue++
        if (currentQuestionIndex.intValue < questions.size) {
            currentQuestion.value = questions[currentQuestionIndex.intValue]
            currentQI.intValue++
        } else {
            val destination = NavigationDestinations.Home.route
            navController.navigate(destination)
        }
    }

    fun updateUserAnswer(answer: String) {
        userAnswer.value = answer
    }
}