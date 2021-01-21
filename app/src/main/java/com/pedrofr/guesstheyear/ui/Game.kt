package com.pedrofr.guesstheyear.ui

import com.pedrofr.guesstheyear.core.GameState
import com.pedrofr.guesstheyear.core.OnGoing
import com.pedrofr.guesstheyear.core.Won

class Game(
    private val questions: List<Question>
) {

    private var questionIndex = -1
    var gameState: GameState = OnGoing

    fun nextQuestion(): Question? {
        if (questionIndex + 1 < questions.size){
            questionIndex++
            return questions[questionIndex]
        }
        gameState = Won
        return null
    }

    fun answer(question: Question, option: String) {
        val result = question.correctOption == option //TODO move this logic into the Question class itself Separation of concerns
        if (result) {
            //TODO increment score
            //TODO update number of correct questions
        } else {
            //todo update number of incorrect questions
        }
    }
}