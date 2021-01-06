package com.pedrofr.guesstheyear.viewmodels

import android.os.CountDownTimer
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedrofr.guesstheyear.data.model.*
import com.pedrofr.guesstheyear.networking.response.TrackResponse
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.math.min

class GameViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    companion object {
        private const val TAG = "FINISHED"
    }

    private var _songs = mutableListOf<TrackResponse>()
    private var questionIndex = -1
    private val _loadingLiveData = MutableLiveData<Boolean>()
    fun getLoading(): LiveData<Boolean> = _loadingLiveData

    private val _errorLiveData = MutableLiveData<Boolean>()
    fun getError(): LiveData<Boolean> = _errorLiveData

    private val _gameState = MutableLiveData<GameState>()
    fun getGameState(): LiveData<GameState> = _gameState

    private val _currentQuestion = MutableLiveData<TrackResponse>()
    fun getCurrentQuestion(): LiveData<TrackResponse> = _currentQuestion

    private val _answers = MutableLiveData<List<String>>()
    fun getAnswers(): LiveData<List<String>> = _answers

    private lateinit var answers: MutableList<String>

    private lateinit var currentSong: TrackResponse

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
        val testDate = LocalDate.parse("2001-03-07")
        Log.d("testDate", testDate.toString())
        _errorLiveData.value = false
        viewModelScope.launch {
            when (val results = repository.getTracks()) {
                is Success -> {
                    _loadingLiveData.value = false
                    _errorLiveData.value = false
                    _songs = results.data.toMutableList()
                    numQuestions = min((_songs.size + 1) / 2, 3)
                    randomizeSongs()
                }
                //TODO
                is Failure -> _errorLiveData.value = true
                Loading -> _loadingLiveData.value = true
            }
        }
        startCountdown()
    }

    // randomize the questions and set the first question
    private fun randomizeSongs() {
        _songs.shuffle()
        setNewSong()
    }

    fun setAnswer(answerIndex: Int) {
        //TODO validate correct answer
//        if (answers[answerIndex] == currentSong.answers[0])
        if (true) {
            _score.value = _score.value?.plus(1)
            setNewSong()
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
                setNewSong()
            }
        }

    }

    // Sets the question and randomizes the answers.
    private fun setNewSong() {
        questionIndex++
        //Advance to next question
        if (questionIndex < numQuestions) {
            currentSong = _songs[questionIndex]
            // randomize the answers into a copy of the array

            val options = mutableListOf<String>(
                "2019",
                "2017",
                "2016",
                "2015"

            )
            answers = options
            // and shuffle them
            answers.shuffle()

            //update list of answers and question
            _answers.value = answers
            _currentQuestion.value = currentSong
            countDownTimer.start()
        } else {
            // We've won!  Navigate to the gameWonFragment.
            _gameState.postValue(Won)

        }
    }

}

