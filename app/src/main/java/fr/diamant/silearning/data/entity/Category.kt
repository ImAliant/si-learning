package fr.diamant.silearning.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

val RANDOM_ID: Int = 1
val HELP_ID: Int = 2

@Entity(tableName = "categories", indices = [Index(value = ["id"], unique = true)])
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)