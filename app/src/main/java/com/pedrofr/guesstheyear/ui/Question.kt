package com.pedrofr.guesstheyear.ui

//TODO see if there's a better way of having a correct option attribute
data class Question(
    val trackTitle: String,
    val trackPreview: String,
    val albumCover: String,
    val options: List<String>, //release year
    val correctOption: String
)
