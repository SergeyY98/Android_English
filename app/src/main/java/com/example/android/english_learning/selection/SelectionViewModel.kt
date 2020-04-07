package com.example.android.english_learning.selection

import android.app.Application
import android.provider.Settings.Global.getString
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.example.android.english_learning.R
import com.example.android.english_learning.database.Question
import com.example.android.english_learning.database.QuestionDatabaseDao
import com.example.android.english_learning.formatStats
import kotlinx.coroutines.*

class SelectionViewModel() : ViewModel() {

    private val _navigateToGame = MutableLiveData<Boolean>()
    val navigateToGame: LiveData<Boolean>
        get() = _navigateToGame

    private lateinit var _mode: String
    val mode: String
        get() = _mode

    private lateinit var _theme: String
    val theme: String
        get() = _theme

    fun doneNavigating() {
        _navigateToGame.value = false
    }

    fun onCheck(checkedThemeId: Int, checkedModeId: Int) {
        if (-1 != checkedThemeId) {
            if (-1 != checkedModeId) {
                _theme = "Sport"
                when (checkedThemeId) {
                    R.id.radioButton_theme2 -> _theme = "Furniture"
                    R.id.radioButton_theme3 -> _theme = "Weapons"
                    R.id.radioButton_theme4 -> _theme = "Cinema"
                    R.id.radioButton_theme5 -> _theme = "Food"
                    R.id.radioButton_theme6 -> _theme = "Technique"
                    R.id.radioButton_theme7 -> _theme = "Keyboard_layout"
                    R.id.radioButton_theme8 -> _theme = "Animals"
                    R.id.radioButton_theme9 -> _theme = "Kitchen"
                    R.id.radioButton_theme10 -> _theme = "Others"
                }
                if (-1 != checkedModeId) {
                    _mode = "English"
                    when (checkedModeId) {
                        R.id.radioButton_mode2 -> _mode = "Russian"
                    }
                }
                _navigateToGame.value = true
            }
        }
    }
}