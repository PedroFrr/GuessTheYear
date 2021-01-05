package com.pedrofr.guesstheyear.networking.response

import com.squareup.moshi.Json

data class TrackResponse(
    @field:Json(name = "title") val title: String,
    @field:Json(name = "release_date") val releaseDate: String,
    @field:Json(name = "preview") val preview: String,
)