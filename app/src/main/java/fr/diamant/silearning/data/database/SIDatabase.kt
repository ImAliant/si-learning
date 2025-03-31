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
        private var INSTANCE: SIDatabase? = null

        fun getDatabase(context: Context): SIDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SIDatabase::class.java,
                        "question_database"
                    ).addCallback(DatabaseCallback(context))
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

    private class DatabaseCallback(private val context: Context): Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            CoroutineScope(Dispatchers.IO).launch {
                prepopulateDatabase(context)
            }
        }

        private suspend fun prepopulateDatabase(context: Context) {
            val dao = INSTANCE?.SIDao()
            val jsonString = readJSONFromAsset(context, "questions.json")
            val type = object : TypeToken<List<CategoryWithQuestionsJSON>>() {}.type
            val data: List<CategoryWithQuestionsJSON> = Gson().fromJson(jsonString, type)
            Logger.getLogger("SIDatabase").info("Populating database with ${data.size} categories")

            dao?.insertCategory(Category(name = context.getString(R.string.random_cat)))
            dao?.insertCategory(Category(name = context.getString(R.string.help_cat)))

            data.forEach { categoryWithQuestions ->
                val categoryId = dao?.getCategoryIdByName(categoryWithQuestions.categoryName)
                    ?: dao?.insertCategory(
                        Category(
                            name = categoryWithQuestions.categoryName
                        )
                    )

                categoryWithQuestions.questions.forEach { questionJSON ->
                    if (categoryId != null) {
                        val imageResId = questionJSON.image?.takeIf { it.isNotEmpty() }?.let {
                            context.resources.getIdentifier(it, "drawable", context.packageName)
                        } ?: 0

                        Logger.getLogger("SIDatabase").info("Inserting question: ${questionJSON.question} with imageResId: $imageResId")

                        dao?.insertQuestion(
                            Question(
                                question = questionJSON.question,
                                answer = questionJSON.answer,
                                image = imageResId,
                                categoryId = categoryId.toInt()
                            )
                        )
                    }
                }
            }
        }

        private fun readJSONFromAsset(context: Context, fileName: String): String {
            return context.assets.open(fileName).bufferedReader().use { it.readText() }
        }
    }
}