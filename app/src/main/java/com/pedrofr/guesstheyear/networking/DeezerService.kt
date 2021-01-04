package com.fevziomurtekin.deezer.domain.network

import com.pedrofr.guesstheyear.data.model.GenreResponse
import retrofit2.http.GET


interface DeezerService{

    //TODO replace with @GET calls

    @GET("genre")
    suspend fun fetchGenreList(): GenreResponse
}