package fr.diamant.silearning.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.diamant.silearning.R
import fr.diamant.silearning.data.dao.SIDao
import fr.diamant.silearning.data.entity.Category
import fr.diamant.silearning.data.entity.CategoryWithQuestionsJSON
import fr.diamant.silearning.data.entity.Question
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.logging.Logger

@Database(entities = [Category::class, Question::class], version = 1, exportSchema = false)
abstract class SIDatabase: RoomDatabase() {
    abstract fun SIDao(): SIDao

    companion object {
        @Volatile
        private var INSTANCE: SIDatabase? = null

        fun getDatabase(context: Context): SIDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    SIDatabase::class.java,
                    "question_database"
                ).addCallback(DatabaseCallback(context))
                    .build().also { INSTANCE = it }
            }
        }
    }

    private class DatabaseCallback(private val context: Context): Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            populateDatabase(context)
        }

        fun populateDatabase(context: Context) {
            CoroutineScope(Dispatchers.IO).launch {
                val dao = INSTANCE?.SIDao()
                val jsonString = readJSONFromAsset(context, "questions.json")
                val type = object : TypeToken<List<CategoryWithQuestionsJSON>>() {}.type
                val data: List<CategoryWithQuestionsJSON> = Gson().fromJson(jsonString, type)
                Logger.getLogger("SIDatabase").info("Populating database with ${data.size} categories")

                dao?.insertCategory(Category(name = context.getString(R.string.random_cat)))

                data.forEach { categoryWithQuestions ->
                    val categoryId = dao?.getCategoryIdByName(categoryWithQuestions.categoryName)
                        ?: dao?.insertCategory(
                            Category(
                                name = categoryWithQuestions.categoryName
                            )
                        )

                    categoryWithQuestions.questions.forEach { questionJSON ->
                        if (categoryId != null) {
                            dao?.insertQuestion(
                                Question(
                                    question = questionJSON.question,
                                    answer = questionJSON.answer,
                                    categoryId = categoryId.toInt()
                                )
                            )
                        }
                    }
                }
            }
        }

        private fun readJSONFromAsset(context: Context, fileName: String): String {
            return context.assets.open(fileName).bufferedReader().use { it.readText() }
        }
    }
}