package com.pedrofr.guesstheyear.domain.model

data class Questions(
    val questionId: String,
    val text: String,
    val answers: List<String>
)
