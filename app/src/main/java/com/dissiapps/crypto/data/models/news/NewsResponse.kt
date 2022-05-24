package com.dissiapps.crypto.data.models.news

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    @SerializedName("results") val newsResults: List<NewsResult>
)