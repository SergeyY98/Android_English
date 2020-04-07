package com.example.android.english_learning.statistics

import android.app.Application
import android.text.Spanned
import androidx.lifecycle.*
import com.example.android.english_learning.database.QuestionDatabaseDao
import com.example.android.english_learning.formatStats
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class StatisticsViewModel(
    val dao: QuestionDatabaseDao,
    application: Application
) : AndroidViewModel(application) {
    data class Stats(
        val mode: String,
        val theme: String,
        val num: Int,
        val rightNum: Int,
        val percentage: Long
    )
    private val stats = mutableListOf<Stats>()

    private val app = application

    val modes = dao.getAllModes()

    lateinit var themes : List<String>

    lateinit var statsString : Spanned

    private val _navigateStatsString = MutableLiveData<Boolean>()
    val navigateStatsString: LiveData<Boolean>
        get() = _navigateStatsString

    init {
        setStatistics()
    }

    fun setStatistics() {
        for (mode in modes) {
            themes = dao.getAllThemes(mode)
            for (theme in themes) {
                stats.add(
                    Stats(
                        mode = mode,
                        theme = theme,
                        num = dao.getNumber(mode, theme),
                        rightNum = dao.getRightNumber(mode, theme),
                        percentage = Math.round(
                            (dao.getRightNumber(mode, theme)).toDouble() / dao.getNumber(mode, theme) * 100
                        )
                    )
                )
            }
        }
        statsString = formatStats(stats, app.resources)
        _navigateStatsString.value = true
    }
}