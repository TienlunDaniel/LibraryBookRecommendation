package com.example.librarybookrecommendation.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.librarybookrecommendation.model.Book
import com.example.librarybookrecommendation.model.BookDao

@Database(entities = [Book::class], version = 1, exportSchema = false)
@TypeConverters(BookConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}