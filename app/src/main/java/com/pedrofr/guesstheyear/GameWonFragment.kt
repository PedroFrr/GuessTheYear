package com.pedrofr.guesstheyear

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.pedrofr.guesstheyear.databinding.FragmentGameWonBinding
import com.pedrofr.guesstheyear.databinding.FragmentTitleBinding
import com.pedrofr.guesstheyear.utils.viewBinding

class GameWonFragment : Fragment(R.layout.fragment_game_won) {

    private val binding by viewBinding(FragmentGameWonBinding::bind)

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)

        //TODO set button actions
    }

}