package com.example.librarybookrecommendation.service

import android.util.Log
import com.example.librarybookrecommendation.LibraryBookApplication
import com.example.librarybookrecommendation.Util.containingLibrary
import com.example.librarybookrecommendation.Util.kingStoneSeedLink
import com.example.librarybookrecommendation.Util.kingStoneSeedLinkInLibrary
import com.example.librarybookrecommendation.Util.scrappingProcessLog
import com.example.librarybookrecommendation.database.AppDatabase
import com.example.librarybookrecommendation.model.Book
import com.example.librarybookrecommendation.model.EmptyBookToScrape
import com.example.librarybookrecommendation.onlineBookStore.OnlineBookStore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object BookDispatcherService {
    fun launchDownloadingService(db: AppDatabase) {
        GlobalScope.launch {
            val bookDao = db.bookDao()
            val bookToScrapeDao = db.bookToScrapeDao()

            while (true){
                /*general looper- unscraped links with default */
                var listToScrape = bookToScrapeDao.getUnscraped(10)
                if (listToScrape.isEmpty())
                    listToScrape = listOf(EmptyBookToScrape)

                /*loop through the list*/
                for( bookToScrape in listToScrape){
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
                }
            }



            val temp = bookDao.getAll()
            temp
        }
    }
}