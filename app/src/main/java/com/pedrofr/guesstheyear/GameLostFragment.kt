package com.pedrofr.guesstheyear

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.pedrofr.guesstheyear.databinding.FragmentGameLostBinding
import com.pedrofr.guesstheyear.databinding.FragmentGameWonBinding
import com.pedrofr.guesstheyear.utils.viewBinding

class GameLostFragment : Fragment(R.layout.fragment_game_lost) {

    private val binding by viewBinding(FragmentGameLostBinding::bind)

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)

        initUi()
    }

    private fun initUi() {
        binding.tryAgainButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_gameLostFragment_to_gameFragment)
        }
    }

}