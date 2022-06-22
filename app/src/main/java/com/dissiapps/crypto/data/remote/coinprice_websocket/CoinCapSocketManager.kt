package com.dissiapps.crypto.data.remote.coinprice_websocket

import com.dissiapps.crypto.data.remote.coinprice_websocket.models.Subscribe
import com.dissiapps.crypto.data.remote.coinprice_websocket.models.Ticker
import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.logging.HttpLoggingInterceptor

private const val CLOSE_CONNECTION_CODE = 1000
private const val BASE_URL = "wss://ws-feed.exchange.coinbase.com"

class CoinCapSocketManager private constructor(
    private val externalScope: CoroutineScope
) {

    companion object {

        private var instance: CoinCapSocketManager? = null

        fun getInstance(externalScope: CoroutineScope) =
            instance ?: synchronized(this) {
                instance ?: CoinCapSocketManager(externalScope).also {
                    instance = it
                }
            }

    }

    private var webSocket: WebSocket? = null

    private val _events = MutableSharedFlow<WebSocketEvent>()
    val socketEvents:SharedFlow<WebSocketEvent> get() = _events

    private val httpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
//            .connectTimeout(10L, TimeUnit.SECONDS)
//            .readTimeout(10L, TimeUnit.SECONDS)
            .build()
    }

    private val gson by lazy {
        Gson()
    }

    /*
    * Sequence numbers that are greater than one integer value from the previous
    * number indicate that a message has been dropped. Sequence numbers that are less
    * than the previous number can be ignored or represent a message that has arrived out of order.
    * */
//    val prevETHSeqNumber = 0L
//    val prevBTCSeqNumber = 0L
    private val coinCapWebSocketListener by lazy {
        object : CoinCapWebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                externalScope.launch {
                    delay(4000)
                    val result = convertStringToModel(text)
                    _events.emit(WebSocketEvent.OnMessageReceived(result))
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                externalScope.launch {
                    _events.emit(WebSocketEvent.OnConnectionFailed(t, response))
                }
            }
        }
    }

    private fun convertStringToModel(string: String): Ticker {
        return gson.fromJson(string, Ticker::class.java)
    }

    @Synchronized
    fun openSocketConnection(){
        if(webSocket == null){
            val request: Request = Request.Builder().url(BASE_URL).build()
            webSocket = okHttpClient.newWebSocket(request, coinCapWebSocketListener)
            okHttpClient.dispatcher.executorService.shutdown()
        }
    }

    fun closeConnection(){
        webSocket?.let {
            val unsubscribe = Subscribe(
                Subscribe.Type.UNSUBSCRIBE,
                emptyList(),
                listOf(Subscribe.Channel.TICKER)
            )
            val string = gson.toJson(unsubscribe).toString()
            it.close(CLOSE_CONNECTION_CODE, string)
            it.cancel()
            webSocket = null
            externalScope.cancel()
            instance = null
        }
    }

    sealed class WebSocketEvent {
        class OnMessageReceived(val ticker: Ticker): WebSocketEvent()
        class OnConnectionFailed(val throwable: Throwable, val response: Response?): WebSocketEvent()
    }
}