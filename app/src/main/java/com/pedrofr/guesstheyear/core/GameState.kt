package com.pedrofr.guesstheyear.core

//TODO revise

sealed class GameState

object Won : GameState()

object Lost : GameState()

object OnGoing : GameState()