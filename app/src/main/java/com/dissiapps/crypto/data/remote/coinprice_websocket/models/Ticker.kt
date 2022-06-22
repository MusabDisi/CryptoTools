package com.dissiapps.crypto.data.remote.coinprice_websocket.models

import com.google.gson.annotations.SerializedName

data class Ticker(
    val type: String,
    @SerializedName("product_id")
    val product: String,
    val sequence: Long,
    val time: String,
    val price: String,
    val side: String,
    @SerializedName("last_size")
    val size: String,
    @SerializedName("best_bid")
    val bestBid: String,
    @SerializedName("best_ask")
    val bestAsk: String
)