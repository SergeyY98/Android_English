package com.example.android.english_learning.game_finish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android.english_learning.R
import com.example.android.english_learning.databinding.FragmentGameFinishBinding
import com.example.android.english_learning.game.GameFragmentArgs

class GameFinishFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentGameFinishBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_game_finish, container, false)
        val args = GameFinishFragmentArgs.fromBundle(arguments!!)
        binding.numCorrect = args.numCorrect
        binding.num = args.num
        binding.startButton.setOnClickListener {
            this.findNavController().navigate(GameFinishFragmentDirections
                .actionGameFinishFragmentToTitleFragment())
        }
        return binding.root
    }
}