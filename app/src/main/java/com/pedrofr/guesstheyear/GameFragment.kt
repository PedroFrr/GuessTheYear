package com.pedrofr.guesstheyear

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.pedrofr.guesstheyear.data.model.Lost
import com.pedrofr.guesstheyear.data.model.Won
import com.pedrofr.guesstheyear.databinding.FragmentGameBinding
import com.pedrofr.guesstheyear.utils.viewBinding
import com.pedrofr.guesstheyear.viewmodels.GameViewModel
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
    }

    private fun initObservables() {
        gameViewModel.getGameState().observe(viewLifecycleOwner) { gameState ->
            when (gameState) {
                Won -> view?.findNavController()
                    ?.navigate(R.id.action_gameFragment_to_gameWonFragment)
                Lost -> view?.findNavController()
                    ?.navigate(R.id.action_gameFragment_to_gameLostFragment)

            }
        }

        gameViewModel.getCurrentQuestion().observe(viewLifecycleOwner) { question ->
            binding.questionText.text = question.text
        }

        gameViewModel.getAnswers().observe(viewLifecycleOwner) { answers ->
            binding.firstAnswerButton.text = answers[0]
            binding.secondAnswerButton.text = answers[1]
            binding.thirdAnswerButton.text = answers[2]
            binding.fourthAnswerButton.text = answers[3]
        }
    }


}