package com.example.librarybookrecommendation.Util

import com.example.librarybookrecommendation.publicLibrary.NewTaipeiLibrary

fun containingLibrary(isbn : String): List<String>{
    val ret = mutableListOf<String>()

    if (NewTaipeiLibrary.hasBook(isbn))
        ret.add(NewTaipeiLibrary.libraryName)
    return ret
}


