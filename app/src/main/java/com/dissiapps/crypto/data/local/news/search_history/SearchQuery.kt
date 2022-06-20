package com.dissiapps.crypto.data.local.news.search_history

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class SearchQuery(
    @PrimaryKey
    val query: String,
    val time: Long
)
