package com.example.librarybookrecommendation

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient


class LibraryBookApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ctx = this
    }

    companion object {
        var ctx: Context? = null
    }
}