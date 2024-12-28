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
import fr.diamant.silearning.error.MessageType
import fr.diamant.silearning.navigation.NavigationDestinations
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.logging.Logger

class GameViewModel(application: Application): AndroidViewModel(application) {
    private val _container = (application as SILearningApplication).container
    private var currentQuestionIndex = mutableIntStateOf(0)
    private var isGameStarted = mutableStateOf(false)
    private var questions = mutableStateListOf<Question>()

    private var delayToRespond = mutableIntStateOf(10)
    private var countdownJob = mutableStateOf<Job?>(null)

    var timerOn = mutableStateOf(false)
    var currentDelay = mutableIntStateOf(delayToRespond.intValue)

    var currentQI = mutableIntStateOf(1)
    var size = mutableIntStateOf(0)

    var currentQuestion = mutableStateOf<Question?>(null)
    var snackbarMessage = mutableStateOf(MessageType.DEFAULT)
    var userAnswer = mutableStateOf("")

    fun initializeGame(categoryId: Int) {
        viewModelScope.launch {
            getQuestions(categoryId)

            if (questions.isNotEmpty() && !isGameStarted.value) {
                initCurrentQuestion()
                isGameStarted.value = true
            } else {
                snackbarMessage.value = MessageType.NO_QUESTION_FOUND_FOR_CATEGORY
            }
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
            currentQuestion.value = questions[currentQuestionIndex.intValue]
        } else {
            snackbarMessage.value = MessageType.NO_QUESTION_FOUND_FOR_CATEGORY
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

    fun startTimer() {
        countdownJob.value?.cancel()
        countdownJob.value = null

        currentDelay.intValue = delayToRespond.intValue
        timerOn.value = true

        countdownJob.value = viewModelScope.launch {
            while (currentDelay.intValue > 0) {
                delay(1000)
                currentDelay.intValue--
            }
            timerOn.value = false
        }
    }

    fun resetTimer() {
        timerOn.value = false
    }
}