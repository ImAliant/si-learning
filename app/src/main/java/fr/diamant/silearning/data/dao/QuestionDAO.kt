package fr.diamant.silearning.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import fr.diamant.silearning.data.entity.Question
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuestion(question: Question)

    @Update
    suspend fun updateQuestion(question: Question)

    @Delete
    suspend fun deleteQuestion(question: Question)

    @Query("SELECT * FROM questions")
    fun getAllQuestionsStream(): Flow<List<Question>>

    @Query("SELECT * FROM questions WHERE id = :id")
    fun getQuestionByIdStream(id: Int): Flow<Question?>
}