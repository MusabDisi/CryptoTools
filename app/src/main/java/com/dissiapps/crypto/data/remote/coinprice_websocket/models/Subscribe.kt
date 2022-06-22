package com.dissiapps.crypto.data.remote.coinprice_websocket.models

import com.google.gson.annotations.SerializedName

data class Subscribe(
    val type: Type,
    @SerializedName("product_ids")
    val productIds: List<ProductId>,
    val channels: List<Channel>
) {
    enum class Type {
        @SerializedName("subscribe") SUBSCRIBE,
        @SerializedName("unsubscribe") UNSUBSCRIBE
    }

    enum class Channel {
        @SerializedName("ticker")
        TICKER
    }

    enum class ProductId(val value: String) {
        @SerializedName("BTC-USD")
        BTC_USD("BTC-USD"),
        @SerializedName("ETH-USD")
        ETH_USD("ETH-USD")
    }
}