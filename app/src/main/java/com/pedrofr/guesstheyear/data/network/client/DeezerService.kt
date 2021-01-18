package com.pedrofr.guesstheyear.data.network.client

import com.pedrofr.guesstheyear.data.network.response.GenreResponse
import com.pedrofr.guesstheyear.data.network.response.TrackResponse
import retrofit2.http.GET
import retrofit2.http.Path


interface DeezerService {

    //TODO replace with @GET calls

    @GET("genre")
    suspend fun fetchGenreList(): GenreResponse

    @GET("track/{trackId}")
    suspend fun fetchTrack(
        @Path("trackId") trackId: Long
    ): TrackResponse
}