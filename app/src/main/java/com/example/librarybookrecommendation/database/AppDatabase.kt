package com.example.librarybookrecommendation.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.librarybookrecommendation.model.Book
import com.example.librarybookrecommendation.model.BookDao
import com.example.librarybookrecommendation.model.BookToScrape
import com.example.librarybookrecommendation.model.BookToScrapeDao

@Database(entities = [Book::class, BookToScrape::class], version = 2, exportSchema = false)
@TypeConverters(BookConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun bookToScrapeDao(): BookToScrapeDao
}