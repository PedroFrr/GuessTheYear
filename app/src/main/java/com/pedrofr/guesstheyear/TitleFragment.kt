package com.pedrofr.guesstheyear

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.pedrofr.guesstheyear.databinding.FragmentTitleBinding
import com.pedrofr.guesstheyear.utils.viewBinding

class TitleFragment : Fragment(R.layout.fragment_title) {

    private val binding by viewBinding(FragmentTitleBinding::bind)

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)

        binding.playButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)

        }

    }
}