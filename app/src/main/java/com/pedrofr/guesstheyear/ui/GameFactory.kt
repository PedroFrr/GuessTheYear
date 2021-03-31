package com.pedrofr.guesstheyear.ui

interface GameFactory {

    suspend fun buildGame(): Game? //TODO see if we can avoid nullable list
}