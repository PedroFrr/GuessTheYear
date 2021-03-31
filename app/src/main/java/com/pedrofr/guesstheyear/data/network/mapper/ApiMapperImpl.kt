package com.pedrofr.guesstheyear.data.network.mapper

import com.pedrofr.guesstheyear.data.db.entities.DbTracks
import com.pedrofr.guesstheyear.data.network.response.TrackResponse
import com.pedrofr.guesstheyear.util.dateToYear
import javax.inject.Inject

class ApiMapperImpl @Inject constructor() : ApiMapper {

    override fun mapTrackResponseToDomain(trackResponses: List<TrackResponse>): List<DbTracks> {
        return trackResponses.map {
            with(it) {
                DbTracks(
                    title = title,
                    releaseYear = releaseDate.dateToYear(),
                    preview = preview,
                    albumCover = album.coverMedium
                )
            }
        }

    }
}
