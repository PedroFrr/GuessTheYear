package com.pedrofr.guesstheyear.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedrofr.guesstheyear.data.model.*
import kotlinx.coroutines.launch
import kotlin.math.min

class GameViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    private var questions = mutableListOf<Question>()
    private var questionIndex = 0
    private val _loadingLiveData = MutableLiveData<Boolean>()
    fun getLoading(): LiveData<Boolean> = _loadingLiveData

    private val _errorLiveData = MutableLiveData<Boolean>()
    fun getError(): LiveData<Boolean> = _errorLiveData

    private val _gameState = MutableLiveData<GameState>()
    fun getGameState(): LiveData<GameState> = _gameState

    private val _currentQuestion = MutableLiveData<Question>()
    fun getCurrentQuestion(): LiveData<Question> = _currentQuestion

    private val _answers = MutableLiveData<List<String>>()
    fun getAnswers(): LiveData<List<String>> = _answers

    private lateinit var answers: MutableList<String>

    private lateinit var currentQuestion: Question

    //Minimum of 3 questions
    private var numQuestions = 0

    init {
        initGame()

    }

    private fun initGame(){
        _errorLiveData.value = false
        viewModelScope.launch {
            when (val results = repository.getQuestions()) {
                is Success -> {
                    _loadingLiveData.value = false
                    _errorLiveData.value = false
                    questions = results.data.toMutableList()
                    numQuestions = min((questions.size + 1) / 2, 3)
                    randomizeQuestions()
                }
                //TODO
                is Failure -> _errorLiveData.value = true
                Loading -> _loadingLiveData.value = true
            }
        }
    }

    // randomize the questions and set the first question
    private fun randomizeQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }

    // Sets the question and randomizes the answers.  This only changes the data, not the UI.
    // Calling invalidateAll on the FragmentGameBinding updates the data.
    private fun setQuestion() {

        currentQuestion = questions[questionIndex]
        // randomize the answers into a copy of the array
        answers = currentQuestion.answers.toMutableList()
        // and shuffle them
        answers.shuffle()

        //update list of answers and question
        _answers.value = answers
        _currentQuestion.value = currentQuestion
    }

    fun setAnswer(answerIndex: Int) {
        if (answers[answerIndex] == currentQuestion.answers[0]) {
            questionIndex++
            //Advance to next question
            if (questionIndex < numQuestions) {
                currentQuestion = questions[questionIndex]
                setQuestion()
            } else {
                // We've won!  Navigate to the gameWonFragment.
                _gameState.postValue(Won)

            }
        } else {
            // Game over! A wrong answer sends us to the gameOverFragment.
            _gameState.postValue(Lost)
        }
    }

}

