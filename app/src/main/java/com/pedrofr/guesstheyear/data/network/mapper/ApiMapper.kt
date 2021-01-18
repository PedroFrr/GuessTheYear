package com.pedrofr.guesstheyear.data.network.mapper

import com.pedrofr.guesstheyear.data.db.entities.DbTracks
import com.pedrofr.guesstheyear.data.network.response.TrackResponse

//TODO change name of classes representing data
interface ApiMapper {

    fun mapTrackResponseToDomain(trackResponses: List<TrackResponse>): List<DbTracks>

}