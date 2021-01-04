package com.pedrofr.guesstheyear.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


/**
 * Data class that represent the a table in the database.
 */
@Entity(tableName = "questions")
data class Question(
    @PrimaryKey val questionId: String = UUID.randomUUID().toString(),
    val text: String,
    val answers: List<String>
)