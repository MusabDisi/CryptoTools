package com.dissiapps.crypto.data.remote.coinprice_websocket

import android.util.Log
import com.dissiapps.crypto.data.remote.coinprice_websocket.models.Subscribe
import com.google.gson.Gson
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

const val TAG = "CoinCapWebSocketL"

open class CoinCapWebSocketListener : WebSocketListener() {

    private val subscribe = Subscribe (
        type = Subscribe.Type.SUBSCRIBE,
        productIds = listOf(Subscribe.ProductId.BTC_USD, Subscribe.ProductId.ETH_USD),
        channels = listOf(Subscribe.Channel.TICKER)
    )

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        Log.d(TAG, "onClosed: ")
        webSocket.send(reason)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.d(TAG, "onMessage: $text")
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.d(TAG, "onOpen: ${response.body} code:${response.code}")
        val message = Gson().toJson(subscribe)
        webSocket.send(message)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.e(TAG, "onFailure: ", t)
    }
}