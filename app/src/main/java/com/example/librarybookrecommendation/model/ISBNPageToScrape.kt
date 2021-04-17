package com.example.librarybookrecommendation.model

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import com.example.librarybookrecommendation.database.BaseDao

@Entity
data class ISBNPageToScrape(
    @PrimaryKey
    val libraryName: String,
    val scrapedPage: List<String>,
    val unScrapedPage: List<String>
)

@Dao
abstract class ISBNPageToScrapeDao : BaseDao<ISBNPageToScrape>() {

    @Query("SELECT * FROM ISBNPageToScrape WHERE libraryName = :name LIMIT 1")
    abstract fun getEntry(name : String) : List<ISBNPageToScrape>
}