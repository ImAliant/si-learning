package fr.diamant.silearning.viewmodel.home

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import fr.diamant.silearning.SILearningApplication
import fr.diamant.silearning.data.entity.Category
import fr.diamant.silearning.message.MessageType
import fr.diamant.silearning.navigation.NavigationDestinations
import kotlinx.coroutines.launch
import java.util.logging.Logger

class HomeViewModel(application: Application): AndroidViewModel(application) {
    private val _container = (application as SILearningApplication).container

    var categories = mutableStateListOf<Category>()
    var snackbarMessage = mutableStateOf(MessageType.DEFAULT)

    init {
        viewModelScope.launch {
            _container.Repository.getAllCategories().collect {
                categories.clear()
                categories.addAll(it)
            }
        }
    }

    fun play(category: Category, navController: NavController) {
        val destination = NavigationDestinations.Game.route + "/${category.id}"
        navController.navigate(destination)
    }
}