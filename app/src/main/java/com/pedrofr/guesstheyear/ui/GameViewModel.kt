package com.pedrofr.guesstheyear.ui

import android.os.CountDownTimer
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedrofr.guesstheyear.core.*
import com.pedrofr.guesstheyear.data.db.entities.DbTracks
import com.pedrofr.guesstheyear.domain.repository.Repository
import kotlinx.coroutines.launch
import kotlin.math.min
import kotlin.random.Random

class GameViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    companion object {
        private const val TAG = "FINISHED"
    }

    private val mediaPlayer = MediaPlayerImpl() //TODO dependecy injection


    //TODO Refactor as this should be on the domain model
    private var _tracks = mutableListOf<DbTracks>()
    private var questionIndex = -1
    private val _loadingLiveData = MutableLiveData<Boolean>()
    fun getLoading(): LiveData<Boolean> = _loadingLiveData

    private val _errorLiveData = MutableLiveData<Boolean>()
    fun getError(): LiveData<Boolean> = _errorLiveData

    private val _gameState = MutableLiveData<GameState>()
    fun getGameState(): LiveData<GameState> = _gameState

    private val _currentQuestion = MutableLiveData<DbTracks>()
    fun getCurrentQuestion(): LiveData<DbTracks> = _currentQuestion

    private val _answers = MutableLiveData<List<String>>()
    fun getAnswers(): LiveData<List<String>> = _answers

    private lateinit var answers: MutableList<String>

    private lateinit var currentTrack: DbTracks

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
            when (val results = repository.getTracks()) {
                is Success -> {
                    _loadingLiveData.value = false
                    _errorLiveData.value = false
                    _tracks = results.data.toMutableList()
                    numQuestions = min((_tracks.size + 1) / 2, 3)
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
        _tracks.shuffle()
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
            currentTrack = _tracks[questionIndex]

            answers = getOptions(currentTrack.releaseYear).toMutableList()
            // and shuffle them
            answers.shuffle()

            //update list of answers and question
            _answers.value = answers
            _currentQuestion.value = currentTrack
            countDownTimer.start()
        } else {
            // We've won!  Navigate to the gameWonFragment.
            _gameState.postValue(Won)

        }
    }

    private fun getOptions(songReleaseYear: Int): List<String> {
        //number of options to produce
        val ints = 1..4
        //interval to add to options
        val randomInterval = Random.nextInt(1, 4)
        val options = ints.scan(songReleaseYear) {acc, _ -> acc + randomInterval}
        return options.map { releaseYear ->
            releaseYear.toString()

        }

    }

    fun getPlayer(): MediaPlayer = mediaPlayer

    fun play(url: String) = mediaPlayer.play(url)

    fun releasePlayer() = mediaPlayer.releasePlayer()

}

