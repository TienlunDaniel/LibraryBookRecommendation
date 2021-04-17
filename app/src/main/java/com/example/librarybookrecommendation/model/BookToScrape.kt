package com.example.librarybookrecommendation.model

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import com.example.librarybookrecommendation.Util.kingStoneSeedLinkInLibrary
import com.example.librarybookrecommendation.database.BaseDao

val EmptyBookToScrape = BookToScrape(kingStoneSeedLinkInLibrary, "", listOf(), false)

@Entity
data class BookToScrape(
    @PrimaryKey
    val link: String,
    val fromLink : String,
    val fromCategories: List<String>,
    val scraped: Boolean = false
)

@Dao
abstract class BookToScrapeDao : BaseDao<BookToScrape>() {

    @Query("SELECT * FROM BookToScrape WHERE scraped = 0 LIMIT :num")
    abstract fun getUnscraped(num: Int): List<BookToScrape>
}