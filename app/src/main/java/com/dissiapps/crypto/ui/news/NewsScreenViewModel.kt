package com.dissiapps.crypto.ui.news

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.dissiapps.crypto.data.local.AppDatabase
import com.dissiapps.crypto.data.remote.coinprice_websocket.CoinCapSocketManager
import com.dissiapps.crypto.data.remote.news.NewsApi
import com.dissiapps.crypto.data.remote.news.NewsPagingSource
import com.dissiapps.crypto.data.remote.news.NewsRemoteMediator
import com.dissiapps.crypto.data.remote.coinprice_websocket.CoinCapSocketManager.WebSocketEvent.*
import com.dissiapps.crypto.data.remote.coinprice_websocket.models.Subscribe
import com.dissiapps.crypto.data.remote.coinprice_websocket.models.Ticker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class NewsScreenViewModel @Inject constructor(
    newsApi: NewsApi,
    database: AppDatabase,
    app: Application
): ViewModel() { // TODO: handle process death

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        enablePlaceholders = true,
        maxSize = 200
    )

    private val newsDao = database.getNewsDao()
    private val _currenciesList = MutableStateFlow(listOf<String>())
    private val coinCapSocketManager = CoinCapSocketManager.getInstance(viewModelScope)

    private val _btcTicker = mutableStateOf<UiState>(UiState.Loading)
    val btcTicker:State<UiState> get() = _btcTicker

    private val _ethTicker = mutableStateOf<UiState>(UiState.Loading)
    val ethTicker:State<UiState> get() = _ethTicker

    init {
        coinCapSocketManager.openSocketConnection()
        viewModelScope.launch {
            coinCapSocketManager.socketEvents.collectLatest {
                when (it) {
                    is OnMessageReceived -> {
                        when(it.ticker.product){
                            Subscribe.ProductId.BTC_USD.value -> {
                                _btcTicker.value = UiState.Success(it.ticker)
                            }
                            Subscribe.ProductId.ETH_USD.value -> {
                                _ethTicker.value = UiState.Success(it.ticker)
                            }
                        }
                    }
                    is OnConnectionFailed -> {
                        _btcTicker.value = UiState.Error
                        _ethTicker.value = UiState.Error
                    }
                }
            }
        }
    }

    private val cachedPager = Pager( pagingConfig,
        remoteMediator = NewsRemoteMediator(database, newsApi, app.applicationContext)
    ) {
        newsDao.getNews()
    }.flow.cachedIn(viewModelScope)


    @OptIn(ExperimentalCoroutinesApi::class)
    val news = _currenciesList.flatMapLatest { lst ->
        if (_currenciesList.value.isEmpty()) {
            cachedPager
        } else {
            Pager(pagingConfig) {
                NewsPagingSource(newsApi, lst)
            }.flow.cachedIn(viewModelScope)
        }
    }

//    override fun onCleared() {
//        super.onCleared()
//        coinCapSocketManager.closeConnection()
//    }

    fun setCurrenciesList(list: List<String>){
        _currenciesList.value = list
    }

    sealed class UiState {
        class Success(val ticker: Ticker): UiState()
        object Error : UiState()
        object Loading: UiState()
    }
}