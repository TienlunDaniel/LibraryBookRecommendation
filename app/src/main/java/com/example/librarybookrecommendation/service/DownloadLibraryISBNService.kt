package com.example.librarybookrecommendation.service

import android.content.Context
import com.example.librarybookrecommendation.Util.getNewTaipeiISBNPage
import com.example.librarybookrecommendation.Util.getUrlHtml
import com.example.librarybookrecommendation.publicLibrary.NewTaipeiLibrary
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object DownloadLibraryISBNService {

    fun newTaipeiCityISBNDownload(context: Context){
        val sharedPref = context.getSharedPreferences(NewTaipeiLibrary.sharedPrefKey, Context.MODE_PRIVATE) ?: return

        GlobalScope.launch {
            val pageNumber = sharedPref.getInt(NewTaipeiLibrary.pageNumberKey, 1)
            val downloadISBNUrl = getNewTaipeiISBNPage(pageNumber = pageNumber.toString())

            val html = getUrlHtml(downloadISBNUrl)
            val temp = html?.getElementsMatchingText("^ISBN/ISSN:(\\d{10}|\\d{13})$")?.map { it.text() }


            with (sharedPref.edit()) {
                putInt(NewTaipeiLibrary.pageNumberKey, pageNumber + 1)
                commit()
            }
        }
    }
}