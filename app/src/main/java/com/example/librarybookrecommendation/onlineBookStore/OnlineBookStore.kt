package com.example.librarybookrecommendation.onlineBookStore

import com.example.librarybookrecommendation.Util.kingStoneStoreRegex
import com.example.librarybookrecommendation.model.Book
import com.example.librarybookrecommendation.model.BookToScrape
import java.lang.RuntimeException

enum class OnlineStoreChoice{
    KINGSTONE
}

abstract class OnlineBookStore{
    abstract val storeName: String
    abstract fun getBookAndFollowingLinks(url : String): Pair<Book, List<BookToScrape>>
    abstract fun isbnBookUrlMapping(isbn : String): String

    companion object {
        fun getOnlineStore(choice: OnlineStoreChoice = OnlineStoreChoice.KINGSTONE) : OnlineBookStore{
            if(choice == OnlineStoreChoice.KINGSTONE){
                return KingStone();
            }

            throw RuntimeException("Invalid Url for online book store")
        }
    }
}




