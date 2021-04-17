package com.example.librarybookrecommendation.Util

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.librarybookrecommendation.LibraryBookApplication
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.coroutines.Continuation
import kotlin.coroutines.suspendCoroutine

var browsers: MutableMap<String, WebView> = ConcurrentHashMap()
var handler: Handler? = null


fun getUrlHtml(url: String): Document? {
    val doc = Jsoup.connect(url).ignoreContentType(true).get()
    return doc
}

suspend fun getUrlHtmlWithCoroutine(key: String, url: String): Document? {
    if (handler == null)
        handler = Handler(Looper.getMainLooper())

    return suspendCoroutine { cont ->

        if (!browsers.containsKey(key))
            getBrowserInstance(key, {
                val doc = Jsoup.parse(it)
                cont.resumeWith(Result.success(doc))
            }) { browsers[key]?.loadUrl(url) }
        else {
            browsers[key]?.loadUrl(url)
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
fun getBrowserInstance(key: String, htmlCB: (String) -> Unit, postCompletion: () -> Unit) {
    handler?.post {
        val browser = WebView(LibraryBookApplication.ctx)
        browsers[key] = browser
        browser.settings.javaScriptEnabled = true
        browser.addJavascriptInterface(MyJavaScriptInterface(htmlCB), "HTMLOUT")

        /* WebViewClient must be set BEFORE calling loadUrl! */
        browser.webViewClient = object :
            WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                /* This call inject JavaScript into the page which just finished loading. */
                GlobalScope.launch {
                    withContext(Dispatchers.Main) {
                        delay(10000)
                        browser.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');")
                    }
                }
            }
        }

        postCompletion()
    }
}

/* An instance of this class will be registered as a JavaScript interface */
class MyJavaScriptInterface(val htmlCB: (String) -> Unit) {
    @JavascriptInterface
    fun processHTML(html: String?) {
        // process the html as needed by the app
        htmlCB(html!!)
    }
}