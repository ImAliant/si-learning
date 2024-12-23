package fr.diamant.silearning.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fr.diamant.silearning.data.dao.QuestionDAO
import fr.diamant.silearning.data.entity.Question

@Database(entities = [Question::class], version = 1, exportSchema = false)
abstract class QuestionDatabase: RoomDatabase() {
    abstract fun questionDAO(): QuestionDAO

    companion object {
        @Volatile
        private var INSTANCE: QuestionDatabase? = null

        fun getDatabase(context: Context): QuestionDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    QuestionDatabase::class.java,
                    "question_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}