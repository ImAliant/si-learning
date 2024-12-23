package fr.diamant.silearning.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class Question(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val question : String,
    val reponse : String
)