package com.pedrofr.guesstheyear.data.model

import androidx.room.Dao
import androidx.room.Query

@Dao
interface QuestionDao {

    @Query("SELECT * FROM questions ORDER BY questionId DESC")
    fun getQuestions(): List<Question>

}