package com.example.librarybookrecommendation.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.librarybookrecommendation.model.*

@Database(entities = [Book::class, BookToScrape::class, ISBNPageToScrape::class], version = 5, exportSchema = false)
@TypeConverters(BookConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun bookToScrapeDao(): BookToScrapeDao
    abstract fun isbnPageToScrapeDao(): ISBNPageToScrapeDao
}