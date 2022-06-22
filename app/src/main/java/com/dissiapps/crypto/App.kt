package com.dissiapps.crypto

import android.app.Application
import com.dissiapps.crypto.data.remote.coinprice_websocket.CoinCapSocketManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.*

@HiltAndroidApp
class App : Application(){

    private val applicationScope = MainScope() + CoroutineName("App")

    companion object {
        lateinit var coinCapSocketManager: CoinCapSocketManager
    }

    override fun onCreate() {
        super.onCreate()
        coinCapSocketManager = CoinCapSocketManager.getInstance(applicationScope)
    }


    override fun onLowMemory() {
        super.onLowMemory()
        coinCapSocketManager.closeConnection()
        applicationScope.cancel()
    }
}