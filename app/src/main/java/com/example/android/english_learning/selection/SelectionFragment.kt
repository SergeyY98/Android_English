package com.example.android.english_learning.selection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.english_learning.databinding.FragmentSelectionBinding
//import com.example.android.english_learning.GameFragmentDirections
import com.example.android.english_learning.R
import com.example.android.english_learning.database.QuestionDatabase

class SelectionFragment : Fragment() {
        // Inflate the layout for this fragment
        private lateinit var viewModel: SelectionViewModel

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {

            val binding: FragmentSelectionBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_selection, container, false)

            viewModel = ViewModelProvider(this).get(SelectionViewModel::class.java)

            binding.button.setOnClickListener {
                val checkedThemeId = binding.themeRadioGroup.checkedRadioButtonId
                val checkedModeId = binding.modeRadioGroup.checkedRadioButtonId
                viewModel.onCheck(checkedThemeId, checkedModeId)
            }

            viewModel.navigateToGame.observe(viewLifecycleOwner, Observer { shouldNavigate ->
                if (shouldNavigate) {
                    findNavController().navigate(
                        SelectionFragmentDirections.actionSelectionFragmentToGameFragment(
                            viewModel.mode,
                            viewModel.theme
                        )
                    )
                    viewModel.doneNavigating()
                }
            })
            return binding.root
        }
    }
