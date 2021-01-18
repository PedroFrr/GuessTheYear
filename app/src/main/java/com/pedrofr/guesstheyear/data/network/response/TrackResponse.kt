package com.pedrofr.guesstheyear.data.network.response

import com.squareup.moshi.Json

data class TrackResponse(
    @Json(name = "title") val title: String,
    @Json(name = "release_date") val releaseDate: String,
    @Json(name = "preview") val preview: String,
)