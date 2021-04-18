package com.example.librarybookrecommendation.model

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import com.example.librarybookrecommendation.Util.kingStoneSeedLinkInLibrary
import com.example.librarybookrecommendation.database.BaseDao

@Entity
data class BookToScrape(
    @PrimaryKey
    val ISBN: String,
    val scraped: Boolean = false,
    val completed : Boolean = false
)

@Dao
abstract class BookToScrapeDao : BaseDao<BookToScrape>() {

    @Query("SELECT * FROM BookToScrape WHERE scraped = 0 LIMIT :num")
    abstract fun getUnscraped(num: Int): List<BookToScrape>

    @Query("SELECT * FROM BookToScrape WHERE completed = 0 AND scraped = 1 LIMIT :num")
    abstract fun getUncompleted(num: Int): List<BookToScrape>

    @Query("SELECT COUNT(*) FROM BookToScrape")
    abstract fun getCount(): Int
}