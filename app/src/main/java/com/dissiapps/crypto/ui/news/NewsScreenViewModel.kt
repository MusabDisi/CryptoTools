package com.dissiapps.crypto.ui.news

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.dissiapps.crypto.data.local.AppDatabase
import com.dissiapps.crypto.data.remote.news.NewsApi
import com.dissiapps.crypto.data.remote.news.NewsRemoteMediator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class NewsScreenViewModel @Inject constructor(
    newsApi: NewsApi,
    database: AppDatabase,
    app: Application
): ViewModel() {

    private val newsDao = database.getNewsDao()

    private val pager = Pager(
        PagingConfig(
            pageSize = 20,
            enablePlaceholders = true,
            maxSize = 200),
        remoteMediator = NewsRemoteMediator(database, newsApi, app.applicationContext)
    ) { newsDao.getNews() }

    val news = pager.flow
}