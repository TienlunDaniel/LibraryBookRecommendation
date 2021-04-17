package com.example.librarybookrecommendation.onlineBookStore

import com.example.librarybookrecommendation.Util.*
import com.example.librarybookrecommendation.model.Book
import com.example.librarybookrecommendation.model.BookToScrape
import com.example.librarybookrecommendation.model.EmptyBook
import org.jsoup.nodes.Document

class KingStone(override val url: String) :
    OnlineBookStore(url) {

    override val storeName: String
        get() = "KingStone"

    override fun getBookAndFollowingLinks(): Pair<Book, List<BookToScrape>> {
        val doc: Document = getUrlHtml(url) ?: return Pair(EmptyBook, listOf())

        val json = doc.getElementsByTag("meta")
            .first { it.attr("name") == "description" }.attr("content")
        val title = json.substring(json.indexOf("書名：") + 3, json.indexOf("，語言"))
        val isbn = json.substring(json.indexOf("ISBN:") + 5, json.indexOf("，出版社"))
        val author = json.substring(json.indexOf("作者:") + 3, json.indexOf("，出版日期"))
        val releaseDate = json.substring(json.indexOf("出版日期:") + 5, json.indexOf("，類別"))
        val description = doc.getElementsByClass("pdintro_txt1field panelCon").text()

        val productID = url.substringAfterLast("/")
        val images =
            doc.getElementsByAttributeValueMatching(
                "href", "" +
                        "$kingStoneImageRegex.*$productID.*"
            ).map { it.attr("href") }

        val similarText = getUrlHtml("$kingStoneSimilarBase$productID")?.text()!!
        val complementText = getUrlHtml("$kingStoneComplementary$productID")?.text()!!

        val similarLink = processProductID(similarText)
        val complementLink = processProductID(complementText)

        var categories = doc.getElementsMatchingText("商品分類").last().text().split("＞")
        categories = categories.subList(1, categories.lastIndex)

        val followLinks = similarLink.toMutableSet()
        followLinks.addAll(complementLink)
        val bookToScrapeList = followLinks.map { BookToScrape(it) }

        val book: Book =
            Book(
                isbn,
                title,
                author,
                description,
                releaseDate,
                categories,
                listOf(storeName),
                images,
                listOf(),
                similarLink,
                complementLink
            )
        return Pair(book, bookToScrapeList)
    }

    private fun processProductID(rawText: String): List<String> {
        return rawText.substring(rawText.indexOf("[") + 1, rawText.indexOf("]")).split(",")
            .map { "$kingStoneBookRegex${it.substring(1, it.length - 1)}" }
    }
}


