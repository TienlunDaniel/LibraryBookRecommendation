package com.example.librarybookrecommendation.model

import androidx.room.*
import com.example.librarybookrecommendation.database.BaseDao
import com.example.librarybookrecommendation.database.BookConverter

val EmptyBook = Book("", "", "", "", "", listOf(), listOf(), listOf(), listOf(), listOf(), listOf())

@Entity
data class Book(
    @PrimaryKey val ISBN: String, val title: String,
    val author: String, val description: String,
    val releaseDate: String,
    val categories: List<String>,
    val storeName: List<String>,
    val images: List<String>,
    val libraries: List<String>,
    val contentBasedLinks: List<String>,
    val collaborativeLinks: List<String>
)

@Dao
abstract class BookDao : BaseDao<Book>() {

    @Query("SELECT * FROM Book")
    abstract fun getAll(): List<Book>
}

