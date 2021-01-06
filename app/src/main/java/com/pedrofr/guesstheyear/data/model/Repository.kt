package com.pedrofr.guesstheyear.data.model

import com.pedrofr.guesstheyear.networking.response.TrackResponse
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getQuestions(): Result<List<Question>>

    suspend fun getTracks(): Result<List<TrackResponse>>
}