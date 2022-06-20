package com.dissiapps.crypto.ui.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.dissiapps.crypto.data.local.AppDatabase
import com.dissiapps.crypto.data.remote.news.NewsApi
import com.dissiapps.crypto.data.remote.news.NewsPagingSource
import com.dissiapps.crypto.data.remote.news.NewsRemoteMediator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
): ViewModel() {

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        enablePlaceholders = true,
        maxSize = 200
    )
    private val newsDao = database.getNewsDao()
    private val _currenciesList = MutableStateFlow(listOf<String>())

    @OptIn(ExperimentalCoroutinesApi::class)
    val news = _currenciesList.flatMapLatest { list ->
        if (list.isEmpty()){
            Pager( pagingConfig,
                remoteMediator = NewsRemoteMediator(database, newsApi, app.applicationContext)
            ) {
                newsDao.getNews()
            }.flow.cachedIn(viewModelScope)
        }else{
            Pager(pagingConfig) {
                NewsPagingSource(newsApi, list)
            }.flow.cachedIn(viewModelScope)
        }
    }

    fun setCurrenciesList(list: List<String>){
        _currenciesList.value = list
    }
}