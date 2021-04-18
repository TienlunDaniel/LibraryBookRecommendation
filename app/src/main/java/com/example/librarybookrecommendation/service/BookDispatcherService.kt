package com.example.librarybookrecommendation.service

import android.util.Log
import com.example.librarybookrecommendation.Util.scrappingLog
import com.example.librarybookrecommendation.database.AppDatabase
import com.example.librarybookrecommendation.model.*
import com.example.librarybookrecommendation.onlineBookStore.OnlineBookStore
import kotlinx.coroutines.*
import java.lang.Exception

object BookDispatcherService {

    private val logKey = scrappingLog + "BookService"

    fun launchDownloadingService(db: AppDatabase) {
        GlobalScope.launch {
            val bookDao = db.bookDao()
            val bookToScrapeDao = db.bookToScrapeDao()
            val coroutineNumber = 10

            while (true) {

                Log.d(logKey, "Books Downloaded: ${bookDao.getCount()}")


                /*general looper- unscraped links with default */
                var listToScrape = bookToScrapeDao.getUnscraped(coroutineNumber)

                if (listToScrape.isEmpty())
                    listToScrape = bookToScrapeDao.getUncompleted(coroutineNumber)

                if (listToScrape.isEmpty()) {
                    delay(5000)
                    continue
                }

                val asyncList =
                    listToScrape.map { async { processBook(it, bookDao, bookToScrapeDao) } }

                awaitAll(*asyncList.toTypedArray())
            }
        }
    }

    private fun processBook(
        bookToScrape: BookToScrape,
        bookDao: BookDao,
        bookToScrapeDao: BookToScrapeDao,
        onlineBookStore: OnlineBookStore = OnlineBookStore.getOnlineStore()
    ) {
        try {
            val bookStoreUrl = onlineBookStore.isbnBookUrlMapping(bookToScrape.ISBN)
            if(bookStoreUrl.isEmpty()){
                bookToScrapeDao.upsert(bookToScrape.copy(scraped = true, completed = true))
                Log.d(logKey, "Book is not found : ${bookToScrape.ISBN}")
                return
            }
            // get book and assign library to the book
            val (book, links) = onlineBookStore.getBookAndFollowingLinks(bookStoreUrl)
            val bookWithLibrary = book.copy(libraries = listOf(onlineBookStore.storeName), ISBN = bookToScrape.ISBN)

            //store into persistence data storage with room
            bookDao.insert(bookWithLibrary)
            bookToScrapeDao.upsert(bookToScrape.copy(scraped = true, completed = true))

            //log to see monitor the process
            Log.d(logKey, "Downloaded : $bookWithLibrary")
        } catch (e: Exception) {
            bookToScrapeDao.upsert(bookToScrape.copy(scraped = true))
            Log.d(logKey, "${bookToScrape.toString()} ${e.message}")
        }
    }
}