package com.example.android.english_learning.game

import android.app.Application
import android.provider.Settings.Global.getString
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.example.android.english_learning.R
import com.example.android.english_learning.database.Question
import com.example.android.english_learning.database.QuestionDatabaseDao
import kotlinx.coroutines.*

class GameViewModel(
    val dao: QuestionDatabaseDao,
    application: Application,
    val mode: String,
    val theme: String) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val questions = dao.getQuestions(mode, theme)

    var numQuestions: Int = 0

    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    var questionIndex: Int = 0
    var questionRightIndex: Int = 0

    private var _currQuesttion = MutableLiveData<Question>()
    val currQuesttion: LiveData<Question>
        get() = _currQuesttion

    private var _answ = MutableLiveData<MutableList<String>>()
    val answ: LiveData<MutableList<String>>
        get() = _answ

    private val _navigateToGameFinish = MutableLiveData<Boolean>()
    val navigateToGameFinish: LiveData<Boolean>
        get() = _navigateToGameFinish

    fun onGameFinish() {
        _navigateToGameFinish.value = true
    }

    fun onGameFinishComplete() {
        _navigateToGameFinish.value = false
    }

    fun setCurrentQuestion() {
        _currQuesttion.value = currentQuestion
    }

    fun setCurrentAnswers() {
        _answ.value = answers
    }

    fun setQuestions() {
        setNumQuestions()
        randomizeQuestions()
    }

    fun setNumQuestions() {
        numQuestions = Math.min(questions.size, 10)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    // randomize the questions and set the first question
    fun randomizeQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }

    // Sets the question and randomizes the answers.  This only changes the data, not the UI.
// Calling invalidateAll on the FragmentGameBinding updates the data.
    private fun setQuestion() {
        currentQuestion = questions[questionIndex]
        setCurrentQuestion()
        // randomize the answers into a copy of the array
        answers = currentQuestion.answers.toMutableList()
        // and shuffle them
        answers.shuffle()
        setCurrentQuestion()
        setCurrentAnswers()
    }

    fun onCheck(checkedId: Int) {
        if (-1 != checkedId) {
            var answerIndex = 0
            when (checkedId) {
                R.id.secondAnswerRadioButton -> answerIndex = 1
                R.id.thirdAnswerRadioButton -> answerIndex = 2
                R.id.fourthAnswerRadioButton -> answerIndex = 3
            }
            // The first answer in the original question is always the correct one, so if our
            // answer matches, we have the correct answer.
            questionIndex++
            if (answers[answerIndex] == currentQuestion.answers[0]) {
                questionRightIndex++
                currentQuestion.status = "answered"
                dao.update(currentQuestion)
            }
            if (questionIndex < numQuestions) {
                currentQuestion = questions[questionIndex]
                setQuestion()
            } else {
                // We've won!  Navigate to the gameWonFragment.
                onGameFinish()
            }
        }
    }
}
