package fr.diamant.silearning.viewmodel.game

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import fr.diamant.silearning.SILearningApplication
import fr.diamant.silearning.data.entity.Question
import fr.diamant.silearning.navigation.NavigationDestinations
import kotlinx.coroutines.launch
import java.util.logging.Logger

class GameViewModel(application: Application): AndroidViewModel(application) {
    private val _container = (application as SILearningApplication).container
    private var currentQuestionIndex = 0
    private var isGameStarted = mutableStateOf(false)
    var currentQuestion: Question? = null
    private var questions = mutableStateListOf<Question>()

    var userAnswer = mutableStateOf("")

    fun initializeGame(categoryId: Int) {
        viewModelScope.launch {
            getQuestions(categoryId)
        }

        if (questions.isNotEmpty() && !isGameStarted.value) {
            initCurrentQuestion()
            isGameStarted.value = true
        }
    }

    private suspend fun getQuestions(categoryId: Int) {
        _container.Repository.getQuestionsByCategoryId(categoryId).collect {
            questions.clear()
            questions.addAll(it)
            shuffleQuestions()
        }
    }

    private fun shuffleQuestions() {
        questions.shuffle()
    }

    private fun initCurrentQuestion() {
        if (questions.isNotEmpty()) {
            currentQuestion = questions[currentQuestionIndex]
        } else {
            Logger.getLogger("GameViewModel").warning("No questions found")
        }
    }

    fun checkAnswer(navController: NavController) {
        if (currentQuestion?.answer == userAnswer.value) {
            Logger.getLogger("GameViewModel").info("Correct answer")
        } else {
            Logger.getLogger("GameViewModel").warning("Wrong answer")
        }
        nextQuestion(navController)
    }

    fun nextQuestion(navController: NavController) {
        currentQuestionIndex++
        if (currentQuestionIndex < questions.size) {
            currentQuestion = questions[currentQuestionIndex]
        } else {
            val destination = NavigationDestinations.Home.route
            navController.navigate(destination)
        }
    }

    fun updateUserAnswer(answer: String) {
        userAnswer.value = answer
    }
}