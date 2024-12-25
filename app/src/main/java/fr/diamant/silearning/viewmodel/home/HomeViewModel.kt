package fr.diamant.silearning.viewmodel.home

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import fr.diamant.silearning.SILearningApplication
import fr.diamant.silearning.data.entity.Category
import kotlinx.coroutines.launch

class HomeViewModel(application: Application): AndroidViewModel(application) {
    private val container = (application as SILearningApplication).container

    val categories = mutableStateListOf<Category>()

    init {
        viewModelScope.launch {
            container.Repository.getAllCategories().collect {
                categories.clear()
                categories.addAll(it)
            }
        }
    }
}