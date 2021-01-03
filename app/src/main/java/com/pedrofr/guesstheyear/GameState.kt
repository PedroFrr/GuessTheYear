package com.pedrofr.guesstheyear

//TODO revise

sealed class GameState

object Won: GameState()

object Lost: GameState()