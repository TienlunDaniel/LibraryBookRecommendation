package com.example.librarybookrecommendation.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookToScrape(
    @PrimaryKey
    val link: String,
    val fromLink : String,
    val fromCategories: List<String>
)