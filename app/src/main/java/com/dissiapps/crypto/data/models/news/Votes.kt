package com.dissiapps.crypto.data.models.news

data class Votes(
    val comments: Int,
    val disliked: Int,
    val important: Int,
    val liked: Int,
    val lol: Int,
    val negative: Int,
    val positive: Int,
    val saved: Int,
    val toxic: Int
)