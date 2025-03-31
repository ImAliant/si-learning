package fr.diamant.silearning.viewmodel.game

import android.app.Application
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import fr.diamant.silearning.SILearningApplication
import fr.diamant.silearning.data.entity.HELP_ID
import fr.diamant.silearning.data.entity.Question
import fr.diamant.silearning.data.entity.RANDOM_ID
import fr.diamant.silearning.message.MessageType
import fr.diamant.silearning.navigation.NavigationDestinations
import kotlinx.coroutines.Dispatchers
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
    var currentNeedHelp = mutableStateOf(false)
    var snackbarMessage = mutableStateOf(MessageType.DEFAULT)
    var showAnswer = mutableStateOf(false)

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
        val fetchedQuestions: List<Question> = when (categoryId) {
            RANDOM_ID -> {
                _container.Repository.getAllQuestions().first().shuffled().take(50)
            }
            HELP_ID -> {
                _container.Repository.getQuestionWhoNeedHelp().first().shuffled()
            }
            else -> {
                _container.Repository.getQuestionsByCategoryId(categoryId).first()
            }
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
            setQuestion(questions[currentQuestionIndex.intValue])
        } else {
            snackbarMessage.value = MessageType.NO_QUESTION_FOUND_FOR_CATEGORY
        }
    }

    // Use to change the current question
    // Called when the user clicks on the previous or next button
    // It will decrement or increment the currentQuestionIndex
    private fun updateCurrentQuestionIndex(index: Int, navController: NavController) {
        currentQuestionIndex.intValue += index
        if (currentQuestionIndex.intValue < 0) {
            currentQuestionIndex.intValue = 0
            currentQI.intValue = 1
        } else if (currentQuestionIndex.intValue >= questions.size) {
            goToHome(navController)
        } else {
            setQuestion(questions[currentQuestionIndex.intValue])
            currentQI.intValue = currentQuestionIndex.intValue + 1
            showAnswer.value = false
        }
    }

    private fun goToHome(navController: NavController) {
        val destination = NavigationDestinations.Home.route
        navController.navigate(destination)
    }

    fun moveToNextQuestion(navController: NavController) {
        updateCurrentQuestionIndex(1, navController)
    }

    fun moveToPreviousQuestion(navController: NavController) {
        updateCurrentQuestionIndex(-1, navController)
    }

    fun changeStatusQuestion() {
        val currentQ = currentQuestion.value
        if (currentQ != null) {
            currentQ.needHelp = !currentQ.needHelp
            currentNeedHelp.value = currentQ.needHelp
            viewModelScope.launch(Dispatchers.IO) {
                _container.Repository.updateQuestion(currentQ)
            }
        }
    }

    fun printAnswer() {
        showAnswer.value = true
    }

    private fun setQuestion(question: Question) {
        currentQuestion.value = question
        currentNeedHelp.value = question.needHelp
    }
}