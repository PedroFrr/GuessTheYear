package com.pedrofr.guesstheyear.data.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AlbumResponse(
    @Json(name = "cover_medium") val coverMedium: String
)
