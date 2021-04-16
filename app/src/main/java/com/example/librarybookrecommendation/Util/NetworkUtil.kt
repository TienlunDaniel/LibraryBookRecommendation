package com.example.librarybookrecommendation.Util

import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.HtmlPage
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


fun getUrlHtml(url: String): Document? {
    val webClient = WebClient()
    val page: HtmlPage = webClient.getPage(url)
    val doc = Jsoup.parse(page.asNormalizedText())
    return doc
}