package com.example.librarybookrecommendation

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.room.Room
import com.example.librarybookrecommendation.database.AppDatabase


class LibraryBookApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ctx = this
//        bookDatabase = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "BookDatabase").build()
    }

    companion object {
        var ctx: Context? = null
        var bookDatabase: AppDatabase? = null
    }
}