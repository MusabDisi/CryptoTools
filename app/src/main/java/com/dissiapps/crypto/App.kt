package com.dissiapps.crypto

import android.app.Application
import com.dissiapps.crypto.data.remote.coinprice_websocket.CoinCapSocketManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.*

@HiltAndroidApp
class App : Application()