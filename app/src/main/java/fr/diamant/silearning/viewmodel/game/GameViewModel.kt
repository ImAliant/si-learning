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
import fr.diamant.silearning.data.entity.RANDOM_ID
import fr.diamant.silearning.message.MessageType
import fr.diamant.silearning.navigation.NavigationDestinations
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class GameViewModel(application: Application): AndroidViewModel(application) {
    private val _container = (application as SILearningApplication).container
    private var currentQuestionIndex = mutableIntStateOf(0)
    private var isGameStarted = mutableStateOf(false)
    private var questions = mutableStateListOf<Question>()

    private var delayToRespond = mutableIntStateOf(5)
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
            fetchQuestions(categoryId)

            if (questions.isNotEmpty() && !isGameStarted.value) {
                setCurrentQuestion()
                isGameStarted.value = true
            } else {
                snackbarMessage.value = MessageType.NO_QUESTION_FOUND_FOR_CATEGORY
            }
        }
    }

    private suspend fun fetchQuestions(categoryId: Int) {
        val fetchedQuestions: List<Question> = if (categoryId == RANDOM_ID) {
            _container.Repository.getAllQuestions().first().shuffled().take(10)
        } else {
            _container.Repository.getQuestionsByCategoryId(categoryId).first()
        }

        questions.clear()
        questions.addAll(fetchedQuestions)
        size.intValue = questions.size
        shuffleQuestions()
    }

    private fun shuffleQuestions() {
        questions.shuffle()
    }

    private fun setCurrentQuestion() {
        if (questions.isNotEmpty()) {
            currentQuestion.value = questions[currentQuestionIndex.intValue]
        } else {
            snackbarMessage.value = MessageType.NO_QUESTION_FOUND_FOR_CATEGORY
        }
    }

    fun checkAnswer(navController: NavController) {
        if (currentQuestion.value?.answer == userAnswer.value) {
            snackbarMessage.value = MessageType.CORRECT_ANSWER
        } else {
            snackbarMessage.value = MessageType.WRONG_ANSWER
        }
        moveToNextQuestion(navController)
    }

    fun moveToNextQuestion(navController: NavController) {
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
        resetCountdownJob()

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

    private fun resetCountdownJob() {
        countdownJob.value?.cancel()
        countdownJob.value = null
    }

    fun resetTimer() {
        timerOn.value = false
    }
}