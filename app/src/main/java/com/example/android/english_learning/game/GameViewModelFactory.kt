package com.example.android.english_learning.game

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.english_learning.database.QuestionDatabaseDao

class GameViewModelFactory(
    private val dao: QuestionDatabaseDao,
    private val application: Application,
    private val mode: String,
    private val theme: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(dao, application, mode, theme) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}