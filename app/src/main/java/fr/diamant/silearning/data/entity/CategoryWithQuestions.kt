package fr.diamant.silearning.data.entity

data class CategoryWithQuestionsJSON(
    val categoryName: String,
    val questions: List<QuestionJSON>
)

data class QuestionJSON(
    val question: String,
    val answer: String,
    val image: String?
)
