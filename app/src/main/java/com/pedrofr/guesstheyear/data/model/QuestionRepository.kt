package com.pedrofr.guesstheyear.data.model

import kotlinx.coroutines.flow.Flow

interface QuestionRepository {

    suspend fun getQuestions(): List<Question>
}