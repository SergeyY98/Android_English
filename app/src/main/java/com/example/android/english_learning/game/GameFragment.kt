package com.example.android.english_learning.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.english_learning.R
import com.example.android.english_learning.database.Question
import com.example.android.english_learning.database.QuestionDatabase
import com.example.android.english_learning.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val binding: FragmentGameBinding = DataBindingUtil.inflate(
        inflater, R.layout.fragment_game, container, false)

        val application = requireNotNull(this.activity).application
        val dao = QuestionDatabase.getInstance(application).getQuestionDatabaseDao()

        val args = GameFragmentArgs.fromBundle(arguments!!)

        val viewModelFactory =
            GameViewModelFactory(
                dao,
                application,
                args.mode,
                args.theme
            )
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(GameViewModel::class.java)

        viewModel.setQuestions()

        viewModel.currQuesttion.observe(viewLifecycleOwner, Observer { currQuesttion ->
            (activity as AppCompatActivity).supportActionBar?.title = getString(
                R.string.english_learning_question_trivia_question,
                viewModel.questionIndex + 1,
                viewModel.numQuestions
            )
            currentQuestion = currQuesttion
            binding.currentQuestion = currentQuestion.text
        })

        viewModel.answ.observe(viewLifecycleOwner, Observer { answ ->
            answers = answ
            binding.answer1 = answers[0].trim(' ')
            binding.answer2 = answers[1].trim(' ')
            binding.answer3 = answers[2].trim(' ')
            binding.answer4 = answers[3].trim(' ')
        })

        binding.submitButton.setOnClickListener {
            viewModel.onCheck(binding.questionRadioGroup.checkedRadioButtonId)
        }

        viewModel.navigateToGameFinish.observe(viewLifecycleOwner, Observer { navigateToGameFinish ->
            if (navigateToGameFinish) {
                findNavController().navigate(
                    GameFragmentDirections.actionGameFragmentToGameFinishFragment(
                        viewModel.questionRightIndex,
                        viewModel.numQuestions
                    )
                )
                viewModel.onGameFinishComplete()
            }
        })
        // Set the onClickListener for the submitButton
        return binding.root
    }
}