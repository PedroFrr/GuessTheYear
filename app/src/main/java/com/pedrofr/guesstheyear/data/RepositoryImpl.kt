package com.pedrofr.guesstheyear.data

import com.pedrofr.guesstheyear.core.Failure
import com.pedrofr.guesstheyear.core.Result
import com.pedrofr.guesstheyear.core.Success
import com.pedrofr.guesstheyear.data.db.dao.TracksDao
import com.pedrofr.guesstheyear.data.db.entities.DbTracks
import com.pedrofr.guesstheyear.data.network.client.DeezerClient
import com.pedrofr.guesstheyear.data.network.mapper.ApiMapper
import com.pedrofr.guesstheyear.domain.repository.Repository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 */
@Singleton
class RepositoryImpl @Inject constructor(
    private val apiMapper: ApiMapper,
    private val deezerClient : DeezerClient,
    private val tracksDao: TracksDao,
) : Repository {

    //TODO in the future first save on DB
    override suspend fun getTracks(): Result<List<DbTracks>> {
        val results = deezerClient.fetchTracks()
        return if (results is Success) {
            Success(apiMapper.mapTrackResponseToDomain(results.data))
        }else {
            //TODO refactor this
            val resultsFailure = results as Failure
            Failure(resultsFailure.error )
        }
    }

}
