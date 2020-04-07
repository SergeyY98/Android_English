package com.example.android.english_learning.database

import androidx.room.TypeConverter

class Converter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromAnswers(answers: List<String?>): String {
            return answers.joinToString()
        }

        @TypeConverter
        @JvmStatic
        fun toAnswers(data: String): List<String> {
            return data.split(",")
        }
    }
}