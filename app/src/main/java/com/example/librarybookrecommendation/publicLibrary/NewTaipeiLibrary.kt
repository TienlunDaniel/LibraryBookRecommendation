package com.example.librarybookrecommendation.publicLibrary

import com.example.librarybookrecommendation.Util.getNewTaipeiPage
import com.example.librarybookrecommendation.Util.getUrlHtml

object NewTaipeiLibrary : PublicLibrary() {
    override val libraryName: String
        get() = "新北市立圖書館"

    override fun hasBook(isbn: String): Boolean {
        val ntc = getNewTaipeiPage(isbn)
        val doc = getUrlHtml(ntc)

        //has book
        return doc!!.getElementsMatchingText("無符合館藏資料").size == 0
    }
}