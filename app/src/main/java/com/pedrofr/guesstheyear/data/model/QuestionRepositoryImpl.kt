package com.pedrofr.guesstheyear.data.model

import com.fevziomurtekin.deezer.domain.network.DeezerClient
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 */
@Singleton
class QuestionRepositoryImpl @Inject constructor(
    private val deezerClient: DeezerClient
) : QuestionRepository {

    override suspend fun getQuestions(): List<Question> {

        val results = deezerClient.fetchQuestions()
        return if (results is Success) {
            results.data
        }else {
            listOf()
        }

    }

}