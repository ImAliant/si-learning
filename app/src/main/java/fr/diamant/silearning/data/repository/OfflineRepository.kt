package fr.diamant.silearning.data.repository

import fr.diamant.silearning.data.dao.SIDao
import fr.diamant.silearning.data.entity.Category
import fr.diamant.silearning.data.entity.Question

class OfflineRepository(private val dao: SIDao): Repository {
    override fun getAllCategories() = dao.getAllCategories()
    override fun getCategoryById(id: Int) = dao.getCategoryById(id)
    override fun getCategoryByName(name: String) = dao.getCategoryByName(name)
    override fun getQuestionsByCategory(categoryId: Int) = dao.getQuestionsByCategory(categoryId)
    override suspend fun insertCategory(category: Category) = dao.insertCategory(category)
    override fun getAllQuestions() = dao.getAllQuestions()
    override fun getQuestionById(id: Int) = dao.getQuestionById(id)
    override suspend fun insertQuestion(question: Question) = dao.insertQuestion(question)
}