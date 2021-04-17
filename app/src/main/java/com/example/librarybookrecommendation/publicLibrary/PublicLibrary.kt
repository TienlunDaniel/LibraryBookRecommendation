package com.example.librarybookrecommendation.publicLibrary

import com.example.librarybookrecommendation.Util.kingStoneStoreRegex
import com.example.librarybookrecommendation.model.Book
import java.lang.RuntimeException

abstract class PublicLibrary{
    abstract val libraryName: String
    abstract fun hasBook(isbn : String): Boolean
}




