package com.example.librarybookrecommendation.model

import androidx.room.*
import com.example.librarybookrecommendation.database.BookConverter

val EmptyBook = Book("", "", "", "", "", listOf(), listOf(), listOf(), listOf(), listOf())

@Entity
data class Book(
    @PrimaryKey val ISBN: String, val title: String,
    val author: String, val description: String,
    val releaseDate: String,
    val categories: List<String>,
    val storeName: List<String>,
    val images: List<String>,
    val libraries: List<String>,
    val relatedBooksLinks: List<String>
)

@Dao
interface BookDao {

    @Query("SELECT * FROM Book")
    fun getAll(): List<Book>

    @Insert
    fun insertAll(vararg books: Book)

    @Delete
    fun delete(book: Book)
}

