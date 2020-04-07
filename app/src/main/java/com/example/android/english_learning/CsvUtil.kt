package com.example.android.english_learning

import android.content.Context
import com.example.android.english_learning.database.Question

class CsvReader(val context: Context) {
    // Открыть файл и получить всё содержимое
    private fun open(fileName: String): String {
        val inputStream = this.context.assets.open(fileName)
        return inputStream.bufferedReader().use { it.readText() }
    }

    // Заполнить csv данными ArrayList
    private fun fullArrayList(csvData: String): MutableList<Question> {
        val simpleList = mutableListOf<Question>()
        val rows: List<String> = csvData.split("\n").map { it.trim() }

        for (row in rows) {
            val cells: List<String> = row.split(";").map { it.trim() }

            simpleList.add(Question(
                mode = cells[0],
                theme = cells[1],
                text = cells[2],
                answers = cells[3].split(",").map { it.trim() }
            ))
        }

        return simpleList
    }

    // Прочитать данных из csv файла в массив
    fun getQuestionList(): MutableList<Question> {
        val csvData = this.open("Questions.csv")
        return fullArrayList(csvData)
    }
}