package fr.diamant.silearning.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.diamant.silearning.data.entity.Category
import fr.diamant.silearning.data.entity.Question
import kotlinx.coroutines.flow.Flow

@Dao
interface SIDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: Question): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category): Long

    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE id = :id")
    fun getCategoryById(id: Int): Flow<Category>

    @Query("SELECT * FROM categories WHERE name = :name")
    fun getCategoryByName(name: String): Flow<Category>

    @Query("SELECT id FROM categories WHERE name = :name LIMIT 1")
    fun getCategoryIdByName(name: String): Long?

    @Query("SELECT * FROM questions WHERE categoryId = :categoryId")
    fun getQuestionsByCategoryId(categoryId: Int): Flow<List<Question>>

    @Query("SELECT * FROM questions WHERE categoryId = (SELECT id FROM categories WHERE name = :categoryName LIMIT 1)")
    fun getQuestionsByCategoryName(categoryName: String): Flow<List<Question>>

    @Query("SELECT * FROM questions WHERE id = :id")
    fun getQuestionById(id: Int): Flow<Question>

    @Query("SELECT * FROM questions")
    fun getAllQuestions(): Flow<List<Question>>
}