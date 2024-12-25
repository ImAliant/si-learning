package fr.diamant.silearning.data.repository

import fr.diamant.silearning.data.dao.SIDao
import fr.diamant.silearning.data.entity.Category
import fr.diamant.silearning.data.entity.Question
import kotlinx.coroutines.flow.Flow

class OfflineRepository(private val dao: SIDao): Repository {
    override fun getAllCategories() = dao.getAllCategories()
    override fun getCategoryById(id: Int) = dao.getCategoryById(id)
    override fun getCategoryByName(name: String) = dao.getCategoryByName(name)
    override fun getQuestionsByCategoryId(categoryId: Int) = dao.getQuestionsByCategoryId(categoryId)
    override fun getQuestionsByCategoryName(categoryName: String) = dao.getQuestionsByCategoryName(categoryName)
    override suspend fun insertCategory(category: Category) = dao.insertCategory(category)
    override fun getAllQuestions() = dao.getAllQuestions()
    override fun getQuestionById(id: Int) = dao.getQuestionById(id)
    override suspend fun insertQuestion(question: Question) = dao.insertQuestion(question)
}