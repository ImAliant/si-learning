package fr.diamant.silearning.viewmodel.game

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import fr.diamant.silearning.SILearningApplication
import fr.diamant.silearning.data.entity.Question
import kotlinx.coroutines.launch

class GameViewModel(application: Application): AndroidViewModel(application) {
    private val _container = (application as SILearningApplication).container

    val questions = mutableStateListOf<Question>()

    fun getQuestions(categoryId: Int) {
        viewModelScope.launch {
            _container.Repository.getQuestionsByCategoryId(categoryId).collect {
                questions.clear()
                questions.addAll(it)
            }
        }
    }
}