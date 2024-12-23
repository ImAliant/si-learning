package fr.diamant.silearning.data.repository

import fr.diamant.silearning.data.dao.QuestionDAO
import fr.diamant.silearning.data.entity.Question
import kotlinx.coroutines.flow.Flow

class OfflineQuestionsRepository(private val questionDAO: QuestionDAO): QuestionRepository {
    override fun getAllQuestionsStream(): Flow<List<Question>> = questionDAO.getAllQuestionsStream()
    override fun getQuestionByIdStream(id: Int): Flow<Question?> = questionDAO.getQuestionByIdStream(id)
    override suspend fun insertQuestion(question: Question) = questionDAO.insertQuestion(question)
    override suspend fun updateQuestion(question: Question) = questionDAO.updateQuestion(question)
    override suspend fun deleteQuestion(question: Question) = questionDAO.deleteQuestion(question)
}