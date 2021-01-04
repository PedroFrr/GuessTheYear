package com.pedrofr.guesstheyear.data.model

//TODO revise

sealed class GameState

object Won: GameState()

object Lost: GameState()