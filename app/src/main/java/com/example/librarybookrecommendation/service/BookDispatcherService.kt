package com.example.librarybookrecommendation.service

import android.util.Log
import com.example.librarybookrecommendation.LibraryBookApplication
import com.example.librarybookrecommendation.Util.containingLibrary
import com.example.librarybookrecommendation.Util.kingStoneSeedLink
import com.example.librarybookrecommendation.Util.kingStoneSeedLinkInLibrary
import com.example.librarybookrecommendation.Util.scrappingProcessLog
import com.example.librarybookrecommendation.database.AppDatabase
import com.example.librarybookrecommendation.model.*
import com.example.librarybookrecommendation.onlineBookStore.OnlineBookStore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import java.lang.Exception

object BookDispatcherService {
    fun launchDownloadingService(db: AppDatabase) {
        GlobalScope.launch {
            val bookDao = db.bookDao()
            val bookToScrapeDao = db.bookToScrapeDao()
            val coroutineNumber = 5

            while (true){
                /*general looper- unscraped links with default */
                var listToScrape = bookToScrapeDao.getUnscraped(coroutineNumber)
                if (listToScrape.isEmpty())
                    listToScrape = listOf(EmptyBookToScrape)

                val asyncList = listToScrape.map { async { processBook(it, bookDao, bookToScrapeDao) } }

                awaitAll(*asyncList.toTypedArray())
            }
        }
    }

    private fun processBook(bookToScrape: BookToScrape, bookDao: BookDao, bookToScrapeDao: BookToScrapeDao){
        try{
            // get book and assign library to the book
            val (book, links) = OnlineBookStore.getOnlineStore(bookToScrape.link)
                .getBookAndFollowingLinks()
            val bookWithLibrary = book.copy(libraries = containingLibrary(isbn = book.ISBN))

            //store into persistence data storage with room
            bookDao.insert(bookWithLibrary)
            val bookToScrapeUpdated = bookToScrape.copy(scraped = true)
            bookToScrapeDao.update(bookToScrapeUpdated)
            bookToScrapeDao.insert(links)

            //log to see monitor the process
            Log.d(scrappingProcessLog, book.toString())
        }catch (e : Exception){
            Log.d(scrappingProcessLog, "${bookToScrape.toString()} ${e.message}")
        }
    }
}