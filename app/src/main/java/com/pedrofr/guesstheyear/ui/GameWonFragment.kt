package com.pedrofr.guesstheyear.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.findNavController
import com.pedrofr.guesstheyear.R
import com.pedrofr.guesstheyear.databinding.FragmentGameWonBinding
import com.pedrofr.guesstheyear.util.viewBinding

class GameWonFragment : Fragment(R.layout.fragment_game_won) {

    private val binding by viewBinding(FragmentGameWonBinding::bind)

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)

        initUi()
    }

    private fun initUi() {
        binding.nextMatchButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_gameWonFragment_to_gameFragment)
        }
    }

}