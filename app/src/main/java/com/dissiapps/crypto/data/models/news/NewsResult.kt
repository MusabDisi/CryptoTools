package com.dissiapps.crypto.data.models.news

data class NewsResult(
    val created_at: String,
    val currencies: List<Currency>,
    val domain: String,
    val id: Int,
    val kind: String,
    val published_at: String,
    val slug: String,
    val source: Source,
    val title: String,
    val url: String,
    val votes: Votes
)