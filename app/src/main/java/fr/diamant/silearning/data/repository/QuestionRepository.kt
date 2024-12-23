package fr.diamant.silearning.data.repository

import fr.diamant.silearning.data.entity.Question
import kotlinx.coroutines.flow.Flow

interface QuestionRepository {
    fun getAllQuestionsStream(): Flow<List<Question>>
    fun getQuestionByIdStream(id: Int): Flow<Question?>
    suspend fun insertQuestion(question: Question)
    suspend fun updateQuestion(question: Question)
    suspend fun deleteQuestion(question: Question)
}