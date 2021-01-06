package com.pedrofr.guesstheyear.data.model

import com.pedrofr.guesstheyear.networking.DeezerClient
import com.pedrofr.guesstheyear.networking.response.TrackResponse
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 */
@Singleton
class RepositoryImpl @Inject constructor(
    private val deezerClient: DeezerClient
) : Repository {

    override suspend fun getQuestions(): Result<List<Question>> {
        val results = deezerClient.fetchQuestions()
        return if (results is Success) {
            Success(results.data)
        } else {
            Failure
        }
    }

    override suspend fun getTracks(): Result<List<TrackResponse>> {
        val results = deezerClient.fetchTracks()
        return if (results is Success) {
            Success(results.data)
        } else {
            Failure
        }
    }

}
