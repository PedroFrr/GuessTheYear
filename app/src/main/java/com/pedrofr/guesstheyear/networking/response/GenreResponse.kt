package com.pedrofr.guesstheyear.networking.response

import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
    val `data`: List<Data>
)