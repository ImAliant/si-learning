package fr.diamant.silearning.data.repository

import fr.diamant.silearning.data.entity.Category
import fr.diamant.silearning.data.entity.Question
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getAllCategories(): Flow<List<Category>>
    fun getCategoryById(id: Int): Flow<Category>
    fun getCategoryByName(name: String): Flow<Category>
    fun getQuestionsByCategoryId(categoryId: Int): Flow<List<Question>>
    fun getQuestionsByCategoryName(categoryName: String): Flow<List<Question>>
    fun getAllQuestions(): Flow<List<Question>>
    fun getQuestionById(id: Int): Flow<Question>

    suspend fun insertQuestion(question: Question): Long
    suspend fun insertCategory(category: Category): Long
}