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
    private val repository: Repository,
    private val factory: GameFactory,
    private val mediaPlayer: MediaPlayer
) : ViewModel() {

    private val _loadingLiveData = MutableLiveData<Boolean>()
    fun getLoading(): LiveData<Boolean> = _loadingLiveData

    private val _errorLiveData = MutableLiveData<Boolean>()
    fun getError(): LiveData<Boolean> = _errorLiveData

    private val _gameState = MutableLiveData<GameState>()
    fun getGameState(): LiveData<GameState> = _gameState

    private val _currentQuestion = MutableLiveData<Question>()
    fun getCurrentQuestion(): LiveData<Question> = _currentQuestion

    private val _score = MutableLiveData(0)
    fun getScore() = _score

    private val initialCountDown: Long = 10000
    private val countDownInterval: Long = 1000
    private lateinit var countDownTimer: CountDownTimer
    private val _timer = MutableLiveData<Long>(0)
    fun getTimer() = _timer

    private var game: Game? = null

    init {
        initGame()

    }

    private fun initGame() {
        _errorLiveData.value = false
        _loadingLiveData.value = true
        viewModelScope.launch {
            game = factory.buildGame()
            game?.let {
                _errorLiveData.value = false
                _loadingLiveData.value = false
                nextQuestion()
            } //?: _errorLiveData.value = false
        }

    }

    fun nextQuestion() {
        game?.let {
            _currentQuestion.value = it.nextQuestion()
            startCountdown()
        }
    }

    fun answerQuestion(option: String) {
        game?.let {
            it.answer(_currentQuestion.value!!, option) //TODO revise this logic
            countDownTimer.cancel()
            _score.value = _score.value?.plus(1)
            nextQuestion()
            _gameState.value = game?.gameState

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
                countDownTimer.cancel()
                nextQuestion()
            }
        }

        countDownTimer.start()
    }


    // Exo player functions
    fun getPlayer(): MediaPlayer = mediaPlayer

    fun play(url: String) = mediaPlayer.play(url)

    fun releasePlayer() = mediaPlayer.releasePlayer()

}

