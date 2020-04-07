package com.example.android.english_learning

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.android.english_learning.CsvReader
import com.example.android.english_learning.database.QuestionDatabaseDao
import com.example.android.english_learning.database.QuestionDatabase
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class QuestionDatabaseTest {

    private lateinit var questionDao: QuestionDatabaseDao
    private lateinit var db: QuestionDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, QuestionDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        questionDao = db.getQuestionDatabaseDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun assertNumber() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        questionDao.insertAll(CsvReader(context).getQuestionList())
        val count = questionDao.getNumber("English", "Others")
        assertEquals(count, 12)
    }
}

