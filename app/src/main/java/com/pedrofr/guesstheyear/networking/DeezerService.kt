package com.fevziomurtekin.deezer.domain.network

import com.pedrofr.guesstheyear.networking.response.GenreResponse
import com.pedrofr.guesstheyear.networking.response.TrackResponse
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