package com.dissiapps.crypto.data.remote.news

import com.dissiapps.crypto.data.local.news.NewsModel
import com.dissiapps.crypto.data.models.news.NewsResult

fun convertToNewsModel(newData: List<NewsResult>?): List<NewsModel> {
    if(newData == null) return emptyList()
    return newData.map {
        NewsModel(
            url = createUrl(it),
            created_at = it.created_at,
            title = it.title,
            sourceDomain = it.source.domain,
            currencies = it.currencies)
    }
}

private fun createUrl(newData: NewsResult): String {
//    return newData.domain + "/" + newData.slug.lowercase() + "/"
    return newData.url
}