package com.pedrofr.guesstheyear.data.db.entities

import java.util.*


/**
 * Data class that represent the a table in the database.
 */
//TODO see if this will be a entity table. If it is we may need to do 1 to many relationship
//@Entity(tableName = "questions_table")
data class DbQuestions(
    val questionId: String = UUID.randomUUID().toString(),
    val text: String,
    val answers: List<String>
)