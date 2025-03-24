package fr.diamant.silearning.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "questions",
    indices = [
        Index(value = ["id"], unique = true),
        Index(value = ["categoryId"])],
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Question(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val question: String,
    val answer: String,
    val image: Int,
    val categoryId: Int,
    var needHelp: Boolean = false
) {
    override fun toString(): String {
        return "Question(id=$id, question='$question', answer='$answer', image='$image', categoryId=$categoryId, needHelp=$needHelp)"
    }
}