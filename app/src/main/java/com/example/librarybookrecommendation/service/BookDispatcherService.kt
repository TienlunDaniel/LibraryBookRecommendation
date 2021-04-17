package com.example.librarybookrecommendation.service

import com.example.librarybookrecommendation.Util.containingLibrary
import com.example.librarybookrecommendation.Util.kingStoneSeedLink
import com.example.librarybookrecommendation.Util.kingStoneSeedLinkInLibrary
import com.example.librarybookrecommendation.database.AppDatabase
import com.example.librarybookrecommendation.model.Book
import com.example.librarybookrecommendation.onlineBookStore.OnlineBookStore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object BookDispatcherService {
    fun launchDownloadingService(db: AppDatabase) {
        GlobalScope.launch {
            val (book, links) = OnlineBookStore.getOnlineStore(kingStoneSeedLinkInLibrary)
                .getBookAndFollowingLinks()
            val bookWithLibrary = book.copy(libraries = containingLibrary(isbn = book.ISBN))
            book

//            val bookDao = LibraryBookApplication.bookDatabase!!.bookDao()
//            bookDao.insert(book)
//
//            val temp = bookDao.getAll()
//            temp
        }
    }
}