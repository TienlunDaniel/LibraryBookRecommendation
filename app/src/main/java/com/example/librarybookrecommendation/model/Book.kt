package com.example.librarybookrecommendation.model

data class Book(
    val ISBN: String, val title: String, val author: String?,
    val description: String, val releaseDate: String, val storeName: List<String>?,
    val images: List<String>?, val libraries: List<String>?
)

val EmptyBook = Book("", "", "", "", "", null, null, null)