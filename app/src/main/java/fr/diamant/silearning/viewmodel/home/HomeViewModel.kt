package fr.diamant.silearning.viewmodel.home

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import fr.diamant.silearning.SILearningApplication
import fr.diamant.silearning.data.entity.Category
import fr.diamant.silearning.error.ErrorType
import fr.diamant.silearning.navigation.NavigationDestinations
import kotlinx.coroutines.launch

class HomeViewModel(application: Application): AndroidViewModel(application) {
    private val _container = (application as SILearningApplication).container

    val categories = mutableStateListOf<Category>()
    var selectedCategories = mutableStateOf<Category?>(null)
    val error = mutableStateOf(ErrorType.DEFAULT)

    init {
        viewModelScope.launch {
            _container.Repository.getAllCategories().collect {
                categories.clear()
                categories.addAll(it)
            }
        }
    }

    fun updateSelection(category: Category) {
        when {
            selectedCategories.value == category -> selectedCategories.value = null
            else -> selectedCategories.value = category
        }
    }

    fun playSelected(navController: NavController) {
        selectedCategories.value?.let {
            val destination = NavigationDestinations.Game.route + "/${it.id}"
            navController.navigate(destination)
        } ?: run {
            error.value = ErrorType.NO_CATEGORY_SELECTED
        }
    }
}