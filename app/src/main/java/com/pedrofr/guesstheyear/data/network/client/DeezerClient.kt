package com.pedrofr.guesstheyear.data.network.client

import com.pedrofr.guesstheyear.core.Failure
import com.pedrofr.guesstheyear.core.Result
import com.pedrofr.guesstheyear.core.Success
import com.pedrofr.guesstheyear.data.network.response.AlbumResponse
import com.pedrofr.guesstheyear.data.network.response.TrackResponse
import kotlinx.coroutines.delay
import javax.inject.Inject

class DeezerClient @Inject constructor(
    private val deezerService: DeezerService
) {

    suspend fun fetchTracks(): Result<List<TrackResponse>> =
        try {
            val tracks = mutableListOf<TrackResponse>()
            for (i in 3135556..3135566) { //TODO review I need to get another way of obtaining famous songs
                val track = deezerService.fetchTrack(i.toLong())
                tracks.add(track)
            }
            Success(tracks)
        } catch (error: Throwable) {
            Failure(error)
        }

}

object RemoteApi {

    //TODO remove when I find an API that returns this data
    /*
    Mock network call for Track list
     */
    suspend fun fetchTracks(): List<TrackResponse> {
        delay(1000)

        val album =
            AlbumResponse(coverMedium = "https://e-cdns-images.dzcdn.net/images/cover/2e018122cb56986277102d2041a592c8/250x250-000000-80-0-0.jpg")

        return listOf(
            TrackResponse(
                title = "Slipping Away",
                releaseDate = "2016-08-16",
                preview = "https://cdns-preview-d.dzcdn.net/stream/c-deda7fa9316d9e9e880d2c6207e92260-8.mp3",
                album = album
            ),
            TrackResponse(
                title = "Questioning my Mind",
                releaseDate = "2002-03-07",
                preview = "https://storage.googleapis.com/exoplayer-test-media-0/Jazz_In_Paris.mp3",
                album = album
            ),
            TrackResponse(
                title = "Blind Love",
                releaseDate = "2003-03-07",
                preview = "https://storage.googleapis.com/exoplayer-test-media-0/Jazz_In_Paris.mp3",
                album = album
            ),
            TrackResponse(
                title = "Left Side",
                releaseDate = "2004-03-07",
                preview = "https://storage.googleapis.com/exoplayer-test-media-0/Jazz_In_Paris.mp3",
                album = album
            ),
            TrackResponse(
                title = "What a Lovely Day",
                releaseDate = "2005-03-07",
                preview = "https://storage.googleapis.com/exoplayer-test-media-0/Jazz_In_Paris.mp3",
                album = album
            ),
        )
    }
}