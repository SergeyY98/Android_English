package com.example.android.english_learning.database

import androidx.room.*

@Entity(tableName = "question_table")
data class Question (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var questionId: Long = 0L,

    @ColumnInfo(name = "mode")
    var mode: String,

    @ColumnInfo(name = "theme")
    val theme: String,

    @ColumnInfo(name = "word")
    var text: String,

    @ColumnInfo(name = "answers")
    var answers: List<String>,

    @ColumnInfo(name = "status")
    var status: String = "not answered"
)