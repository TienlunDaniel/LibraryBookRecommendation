package com.example.librarybookrecommendation.onlineBookStore

import com.example.librarybookrecommendation.Util.kingStoneStoreRegex
import com.example.librarybookrecommendation.model.Book
import com.example.librarybookrecommendation.model.BookToScrape
import java.lang.RuntimeException

abstract class OnlineBookStore(open val url: String){
    abstract val storeName: String
    abstract fun getBookAndFollowingLinks(): Pair<Book, List<BookToScrape>>

    companion object {
        fun getOnlineStore(url: String) : OnlineBookStore{
            if(url.contains(kingStoneStoreRegex)){
                return KingStone(url);
            }

            throw RuntimeException("Invalid Url for online book store")
        }
    }
}




