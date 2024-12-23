package fr.diamant.silearning.data

import android.content.Context
import fr.diamant.silearning.data.database.QuestionDatabase
import fr.diamant.silearning.data.repository.OfflineQuestionsRepository
import fr.diamant.silearning.data.repository.QuestionRepository

interface AppContainer {
    val questionRepository: QuestionRepository
}

class AppDataContainer(private val context: Context): AppContainer {
    private val database by lazy { QuestionDatabase.getDatabase(context) }
    override val questionRepository: QuestionRepository by lazy { OfflineQuestionsRepository(database.questionDAO()) }
}