package com.pedrofr.guesstheyear.ui

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.pedrofr.guesstheyear.R
import com.pedrofr.guesstheyear.core.Lost
import com.pedrofr.guesstheyear.core.Won
import com.pedrofr.guesstheyear.databinding.FragmentGameBinding
import com.pedrofr.guesstheyear.util.gone
import com.pedrofr.guesstheyear.util.loadImage
import com.pedrofr.guesstheyear.util.viewBinding
import com.pedrofr.guesstheyear.util.visible
import dagger.hilt.android.AndroidEntryPoint

//TODO start player on onStart, onResume...

@AndroidEntryPoint
class GameFragment : Fragment(R.layout.fragment_game) {

    private val binding by viewBinding(FragmentGameBinding::bind)

    private val gameViewModel by viewModels<GameViewModel>()

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)

        initUi()
        initObservables()
    }

    override fun onPause() {
        super.onPause()
        super.onPause()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            gameViewModel.releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            gameViewModel.releasePlayer()
        }
    }


    private fun initUi() {

        binding.firstAnswerButton.setOnClickListener {
            gameViewModel.setAnswer(answerIndex = 0)
        }
        binding.secondAnswerButton.setOnClickListener {
            gameViewModel.setAnswer(answerIndex = 1)
        }
        binding.thirdAnswerButton.setOnClickListener {
            gameViewModel.setAnswer(answerIndex = 2)
        }
        binding.fourthAnswerButton.setOnClickListener {
            gameViewModel.setAnswer(answerIndex = 3)
        }

        binding.videoView.player = gameViewModel.getPlayer().getPlayerImpl(requireContext())

    }

    private fun initObservables() {

        gameViewModel.getLoading().observe(viewLifecycleOwner) {
            it?.let { loading -> showLoading(loading) }
        }

        gameViewModel.getGameState().observe(viewLifecycleOwner) { gameState ->
            when (gameState) {
                Won -> view?.findNavController()
                    ?.navigate(R.id.action_gameFragment_to_gameWonFragment)
                Lost -> view?.findNavController()
                    ?.navigate(R.id.action_gameFragment_to_gameLostFragment)

            }
        }

        gameViewModel.getCurrentQuestion().observe(viewLifecycleOwner) { question ->
            if (question.title.isNotEmpty()) {
                gameViewModel.play(question.preview)
                binding.questionText.text = question.title

                binding.videoViewCover.loadImage(question.albumCover)

                gameViewModel.play(question.preview) //TODO review
                binding.mainGroup.visible()

            } else {
                binding.mainGroup.visibility = View.GONE
            }

        }

        gameViewModel.getScore().observe(viewLifecycleOwner) { score ->
            showScore(score)
        }

        gameViewModel.getTimer().observe(viewLifecycleOwner) { timer ->
            updateTimer(timer)
        }

        gameViewModel.getAnswers().observe(viewLifecycleOwner) { answers ->
            binding.firstAnswerButton.text = answers[0]
            binding.secondAnswerButton.text = answers[1]
            binding.thirdAnswerButton.text = answers[2]
            binding.fourthAnswerButton.text = answers[3]
        }

    }

    private fun showLoading(show: Boolean) {
        binding.mainGroup.gone()
        if(show) binding.loadingLayout.loadingContainer.visible() else binding.loadingLayout.loadingContainer.gone()
    }

    private fun showError(show: Boolean) {
        //TODO
    }

    private fun showScore(score: Int) {
        binding.scoreTextView.text = getString(R.string.game_score, score)
    }

    private fun updateTimer(timer: Long) {
        binding.timerTextView.text = getString(R.string.question_timer, timer)
    }

}