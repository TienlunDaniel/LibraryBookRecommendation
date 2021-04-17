package com.example.librarybookrecommendation.Util

val kingStoneStoreRegex = "www.kingstone.com.tw"
val kingStoneSeedLink = "https://www.kingstone.com.tw/basic/2011760339994"
val kingStoneSeedLinkInLibrary = "https://www.kingstone.com.tw/basic/2011771286638"
val kingStoneImageRegex = "https://cdn.kingstone.com.tw/book/images/product"
val kingStoneBookRegex = "https://www.kingstone.com.tw/basic/"
val kingStoneSimilarBase =
    "https://recommendation.api.useinsider.com/10002708/zh_tw/similar/product/"
val kingStoneComplementary =
    "https://recommendation.api.useinsider.com/10002708/zh_tw/complementary/product/"

val scrappingProcessLog = "scrappingProcessLog"

fun getNewTaipeiISBNPage(pageNumber: String): String {
    return "https://webpac.tphcc.gov.tw/webpac/search.cfm?m=as&k0=*&t0=t&c0=and&y10=&y20=&cat0=&dt0=&l0=&lv0=&lc0=&mt=&at=&sj=&py=&pr=&it=&lr=&lg=&list_num=100&current_page=$pageNumber&si=2&view=d"
}

fun getNewTaipeiPage(isbn: String): String {
    return "https://webpac.tphcc.gov.tw/webpac/search.cfm?m=ss&k0=$isbn&t0=k&c0=and&cat0=&dt0=&l0=&lv0=&lc0=&y10=&y20="
}
