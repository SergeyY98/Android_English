package com.example.android.english_learning.statistics

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.english_learning.R
import com.example.android.english_learning.database.QuestionDatabase
import com.example.android.english_learning.databinding.FragmentStatisticsBinding

class StatisticsFragment : Fragment() {

    private lateinit var viewModel: StatisticsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentStatisticsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_statistics, container, false
        )

        val application = requireNotNull(this.activity).application
        val dao = QuestionDatabase.getInstance(application).getQuestionDatabaseDao()

        val viewModelFactory =
            StatisticsViewModelFactory(
                dao,
                application
            )

        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(StatisticsViewModel::class.java)

        viewModel.navigateStatsString.observe(viewLifecycleOwner, Observer { navigateStatsString ->
            if (navigateStatsString) {
                binding.textview.text = viewModel.statsString
            }
        })

        return binding.root
    }
}
