package com.example.android.english_learning.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.android.english_learning.CsvReader
import java.util.concurrent.Executors


@Database(entities = [Question::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class QuestionDatabase : RoomDatabase() {

    abstract fun getQuestionDatabaseDao(): QuestionDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: QuestionDatabase? = null

        fun getInstance(context: Context): QuestionDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        QuestionDatabase::class.java, "question_db")
                        .addCallback(object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                Executors.newSingleThreadScheduledExecutor()
                                    .execute { getInstance(context).getQuestionDatabaseDao().insertAll(CsvReader(context).getQuestionList())}
                            }
                        })
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}