package com.pedrofr.guesstheyear.data.model

import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getQuestions(): Result<List<Question>>
}