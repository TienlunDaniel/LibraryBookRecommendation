package com.example.librarybookrecommendation

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.room.Room
import com.example.librarybookrecommendation.database.AppDatabase
import com.example.librarybookrecommendation.service.BookDispatcherService
import com.example.librarybookrecommendation.service.DownloadLibraryISBNService


class LibraryBookApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ctx = this
        bookDatabase = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "BookDatabase")
            .fallbackToDestructiveMigration().build()

        DownloadLibraryISBNService.newTaipeiCityISBNDownload(ctx as Context)
        BookDispatcherService.launchDownloadingService(bookDatabase as AppDatabase)
    }

    companion object {
        var ctx: Context? = null
        var bookDatabase: AppDatabase? = null
    }
}