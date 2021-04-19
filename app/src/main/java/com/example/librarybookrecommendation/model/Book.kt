package com.example.librarybookrecommendation.model

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.librarybookrecommendation.database.BaseDao
import com.example.librarybookrecommendation.database.BookConverter
import java.io.Serializable

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
) : Serializable

@Dao
abstract class BookDao : BaseDao<Book>() {

    @Query("SELECT * FROM Book")
    abstract fun getAll(): List<Book>

    @Query("SELECT COUNT(*) FROM Book")
    abstract fun getCount(): Int

    @Query("SELECT COUNT(*) FROM Book")
    abstract fun getCountLiveData(): LiveData<Int>

    @Query("SELECT * FROM Book ORDER BY RANDOM() LIMIT :num")
    abstract fun getBooks(num : Int): List<Book>
}

