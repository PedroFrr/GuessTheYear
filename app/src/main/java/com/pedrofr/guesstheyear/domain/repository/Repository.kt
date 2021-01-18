package com.pedrofr.guesstheyear.domain.repository

import com.pedrofr.guesstheyear.core.Result
import com.pedrofr.guesstheyear.data.db.entities.DbTracks

interface Repository {

    suspend fun getTracks(): Result<List<DbTracks>>
}