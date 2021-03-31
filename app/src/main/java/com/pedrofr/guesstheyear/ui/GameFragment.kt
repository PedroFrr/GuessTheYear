package com.pedrofr.guesstheyear.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.pedrofr.guesstheyear.R
import com.pedrofr.guesstheyear.core.Lost
import com.pedrofr.guesstheyear.core.Won
import com.pedrofr.guesstheyear.databinding.FragmentGameBinding
import com.pedrofr.guesstheyear.util.gone
import com.pedrofr.guesstheyear.util.loadImage
import com.pedrofr.guesstheyear.util.visible
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint

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

        //TODO revise this logic
        binding.apply {
            firstAnswerButton.setOnClickListener {
                gameViewModel.answerQuestion(firstAnswerButton.text.toString())
            }
            secondAnswerButton.setOnClickListener {
                gameViewModel.answerQuestion(secondAnswerButton.text.toString())
            }
            thirdAnswerButton.setOnClickListener {
                gameViewModel.answerQuestion(thirdAnswerButton.text.toString())
            }
            fourthAnswerButton.setOnClickListener {
                gameViewModel.answerQuestion(fourthAnswerButton.text.toString())
            }

            videoView.player = gameViewModel.getPlayer().getPlayerImpl(requireContext())
        }

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

            //TODO review as I don't think it should be necessary to have this here
            if (question != null) {
                gameViewModel.play(question.trackPreview)

                binding.apply {
                    //Set question data
                    questionText.text = question.trackTitle
                    videoViewCover.loadImage(question.albumCover)

                    //set question options
                    firstAnswerButton.text = question.options[0]
                    secondAnswerButton.text = question.options[1]
                    thirdAnswerButton.text = question.options[2]
                    fourthAnswerButton.text = question.options[3]

                    mainGroup.visible()
                }
            }

        }

        gameViewModel.getScore().observe(viewLifecycleOwner) { score ->
            binding.scoreTextView.text = getString(R.string.game_score, score)
        }

        gameViewModel.getTimer().observe(viewLifecycleOwner) { timer ->
            binding.timerTextView.text = getString(R.string.question_timer, timer)
        }


    }

    private fun showLoading(show: Boolean) {
        binding.mainGroup.gone()
        if (show) binding.loadingLayout.loadingContainer.visible() else binding.loadingLayout.loadingContainer.gone()
    }

    private fun showError(show: Boolean) {
        //TODO
    }

}