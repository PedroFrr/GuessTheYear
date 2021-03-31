package com.pedrofr.guesstheyear.ui

import com.pedrofr.guesstheyear.core.GameState
import com.pedrofr.guesstheyear.core.OnGoing
import com.pedrofr.guesstheyear.core.Won

class Game(
    private val questions: List<Question>
) {

    private var questionIndex = -1
    var gameState: GameState = OnGoing

    var score: Int = 0 //TODO maybe move onto its own class

    private val currentQuestion: Question
        get() = questions[questionIndex]

    fun nextQuestion(): Question? {
        if (questionIndex + 1 < questions.size) {
            questionIndex++
            return currentQuestion
        }
        gameState = Won
        return null
    }

    fun answer(option: String) {
        val result =
            currentQuestion.correctOption == option //TODO move this logic into the Question class itself Separation of concerns
        if (result) {
            score++
            //TODO update number of correct questions
        } else {
            //todo update number of incorrect questions
        }
    }
}