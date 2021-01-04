package com.pedrofr.guesstheyear.viewmodels

import android.os.CountDownTimer
import android.util.Log
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

    companion object {
        private const val TAG = "FINISHED"
    }

    private var questions = mutableListOf<Question>()
    private var questionIndex = -1
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

    private val _score = MutableLiveData(0)
    fun getScore() = _score

    private val initialCountDown: Long = 10000
    private val countDownInterval: Long = 1000
    private lateinit var countDownTimer: CountDownTimer
    private val _timer = MutableLiveData<Long>(0)
    fun getTimer() = _timer

    init {
        initGame()

    }

    private fun initGame() {
        _errorLiveData.value = false
        viewModelScope.launch {
            when (val results = repository.getQuestions()) {
                is Success -> {
                    _loadingLiveData.value = false
                    _errorLiveData.value = false
                    questions = results.data.toMutableList()
                    numQuestions =  min((questions.size + 1) / 2, 3)
                    randomizeQuestions()
                }
                //TODO
                is Failure -> _errorLiveData.value = true
                Loading -> _loadingLiveData.value = true
            }
        }
        startCountdown()
    }

    // randomize the questions and set the first question
    private fun randomizeQuestions() {
        questions.shuffle()
        setNewQuestion()
    }

    fun setAnswer(answerIndex: Int) {
        if (answers[answerIndex] == currentQuestion.answers[0]) {
            _score.value = _score.value?.plus(1)
            setNewQuestion()
        } else {
            //TODO for now one wrong answer means GameLost
            _gameState.postValue(Lost)
        }

    }

    private fun startCountdown() {
        val initialTimeLeft = initialCountDown / 1000
        _timer.value = initialTimeLeft

        countDownTimer = object : CountDownTimer(initialCountDown, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                val timeLeft = millisUntilFinished / 1000
                _timer.value = timeLeft
            }

            override fun onFinish() {
                setNewQuestion()
            }
        }

    }

    // Sets the question and randomizes the answers.
    private fun setNewQuestion() {
        questionIndex++
        //Advance to next question
        if (questionIndex < numQuestions) {
            currentQuestion = questions[questionIndex]
            // randomize the answers into a copy of the array
            answers = currentQuestion.answers.toMutableList()
            // and shuffle them
            answers.shuffle()

            //update list of answers and question
            _answers.value = answers
            _currentQuestion.value = currentQuestion
            countDownTimer.start()
        } else {
            // We've won!  Navigate to the gameWonFragment.
            _gameState.postValue(Won)

        }
    }

}

