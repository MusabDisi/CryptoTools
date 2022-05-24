package com.dissiapps.crypto.data.local.news

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dissiapps.crypto.data.models.news.Currency

@Entity(tableName = "news_table")
data class NewsModel(
    val url: String,
    val created_at: String,
    val title: String,
    val sourceDomain: String,
    val currencies: List<Currency>?,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)