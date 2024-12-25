package fr.diamant.silearning.data.entity

import androidx.room.Embedded
import androidx.room.Relation

/*data class CategoryWithQuestions(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val questions: List<Question>
)*/

data class CategoryWithQuestionsJSON(
    val categoryName: String,
    val questions: List<QuestionJSON>
)

data class QuestionJSON(
    val question: String,
    val answer: String
)
