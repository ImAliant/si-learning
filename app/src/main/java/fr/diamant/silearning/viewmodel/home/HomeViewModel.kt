package fr.diamant.silearning.viewmodel.home

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import fr.diamant.silearning.SILearningApplication
import fr.diamant.silearning.data.entity.Category
import fr.diamant.silearning.data.entity.Question
import kotlinx.coroutines.launch

class HomeViewModel(application: Application): AndroidViewModel(application) {
    private val container = (application as SILearningApplication).container

    //val categories = mutableStateListOf<Category>()
    val questions = mutableStateListOf<Question>()

    init {
        viewModelScope.launch {
            container.Repository.getQuestionsByCategoryName("EnterpriseIS").collect {
                questions.clear()
                questions.addAll(it)
            }
        }
    }
}