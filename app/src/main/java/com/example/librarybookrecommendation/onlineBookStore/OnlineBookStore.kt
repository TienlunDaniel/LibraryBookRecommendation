package com.example.librarybookrecommendation.onlineBookStore

import com.example.librarybookrecommendation.Util.kingStoneStoreRegex
import com.example.librarybookrecommendation.model.Book
import java.lang.RuntimeException

abstract class OnlineBookStore(open val url: String){
    abstract val storeName: String
    abstract suspend fun getBook(): Book

    companion object {
        fun getOnlineStore(url: String) : OnlineBookStore{
            if(url.contains(kingStoneStoreRegex)){
                return KingStone(url);
            }

            throw RuntimeException("Invalid Url for online book store")
        }
    }
}




