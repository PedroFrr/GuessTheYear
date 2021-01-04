package com.pedrofr.guesstheyear.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
    val `data`: List<Data>
)