package com.example.librarybookrecommendation.service

import android.content.Context
import android.util.Log
import com.example.librarybookrecommendation.Util.getNewTaipeiISBNPage
import com.example.librarybookrecommendation.Util.getUrlHtml
import com.example.librarybookrecommendation.Util.scrappingProcessLog
import com.example.librarybookrecommendation.database.AppDatabase
import com.example.librarybookrecommendation.model.BookToScrape
import com.example.librarybookrecommendation.model.ISBNPageToScrape
import com.example.librarybookrecommendation.publicLibrary.NewTaipeiLibrary
import kotlinx.coroutines.*
import java.lang.Exception

object DownloadLibraryISBNService {

    fun newTaipeiCityISBNDownload(context: Context, appDatabase: AppDatabase) {
        val bookToScrapeDao = appDatabase.bookToScrapeDao()
        val isbnPageToScrapeDao = appDatabase.isbnPageToScrapeDao()
        val totalPages = 5877
        val numberOfCorountine = 10

        GlobalScope.launch {
            while (true) {

                val rows =
                    isbnPageToScrapeDao.getEntry(NewTaipeiLibrary.libraryName)

                val entry =
                    if (rows.isEmpty())
                        ISBNPageToScrape(
                            NewTaipeiLibrary.libraryName,
                            listOf(),
                            IntRange(1, totalPages).map { it.toString() }
                        )
                    else rows[0]

                Log.d(scrappingProcessLog, "isbnPageToScrapeDao ${bookToScrapeDao.getCount()}")

                val unscrapedPages = entry.unScrapedPage.toMutableList()
                val scrapedPages = entry.scrapedPage.toMutableList()

                val limitPages = unscrapedPages.subList(
                    0, if (unscrapedPages.size > numberOfCorountine)
                        numberOfCorountine else unscrapedPages.size
                )

                if (unscrapedPages.isNullOrEmpty())
                    break

                val asyncList = mutableListOf<Deferred<Unit>>()

                for (unscraped in limitPages) {
                    asyncList.add(async {
                        try {
                            val downloadISBNUrl = getNewTaipeiISBNPage(pageNumber = unscraped)

                            val html = getUrlHtml(downloadISBNUrl)
                            val ISBNs =
                                html!!.getElementsMatchingText("^ISBN/ISSN:(\\d{10}|\\d{13})$")
                                    .map { it.text() }.map {
                                        val isbn = it.substring(it.indexOf(":") + 1)
                                        BookToScrape(isbn, scraped = false, completed = false)
                                    }
                            bookToScrapeDao.insertAll(ISBNs)
                            scrapedPages.add(unscraped)
                            unscrapedPages.remove(unscraped)
                            Log.d(scrappingProcessLog, "$unscraped $ISBNs")
                        } catch (e: Exception) {
                            Log.d(scrappingProcessLog, unscraped + " " + e.message)
                        }
                        return@async
                    })
                }

                awaitAll(*asyncList.toTypedArray())

                isbnPageToScrapeDao.upsert(
                    entry.copy(
                        scrapedPage = scrapedPages,
                        unScrapedPage = unscrapedPages
                    )
                )
            }
        }
    }
}
