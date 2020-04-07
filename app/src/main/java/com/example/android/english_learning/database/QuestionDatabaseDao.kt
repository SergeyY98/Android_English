package com.example.android.english_learning.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface QuestionDatabaseDao {
    @Insert
    fun insertAll(questions: MutableList<Question>)

    @Update
    fun update(question: Question)

    @Query("DELETE FROM question_table")
    fun clear()

    @Query("SELECT COUNT(*) FROM  question_table WHERE mode = :mode AND theme = :theme")
    fun getNumber(mode: String, theme: String): Int

    @Query("SELECT COUNT (*) FROM question_table WHERE status = 'answered' AND mode = :mode AND theme = :theme")
    fun getRightNumber(mode: String, theme: String): Int

    @Query("SELECT * FROM question_table WHERE mode = :mode AND theme = :theme")
    fun getQuestions(mode: String, theme: String): MutableList<Question>

    @Query("SELECT DISTINCT mode FROM question_table")
    fun getAllModes(): List<String>

    @Query("SELECT DISTINCT theme FROM question_table WHERE mode = :mode")
    fun getAllThemes(mode: String): List<String>
}