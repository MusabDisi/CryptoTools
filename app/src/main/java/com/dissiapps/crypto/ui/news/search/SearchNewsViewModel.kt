package com.dissiapps.crypto.ui.news.search

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dissiapps.crypto.data.local.AppDatabase
import com.dissiapps.crypto.data.local.news.search_history.SearchQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    val database: AppDatabase // TODO: inject dao instead
): ViewModel() {

    private val searchHistoryDao = database.getSearchHistoryDao()
    val searchHistory = mutableStateOf<List<SearchQuery>>(emptyList())

    init {
        viewModelScope.launch {
            searchHistoryDao.getHistory().collect{
                searchHistory.value = it
            }
        }
    }

    fun insertValueToHistory(value: String) {
        Log.e("TAG", "insertValueToHistory: -----------------------------------------------------")
        CoroutineScope(Dispatchers.IO).launch { // TODO: move to repo
            searchHistoryDao.insertSearchQuery(
                SearchQuery(
                    time = System.currentTimeMillis(),
                    query = value
                )
            )
        }
    }

    fun deleteQuery(query: SearchQuery){
        CoroutineScope(Dispatchers.IO).launch { // TODO: move to repo
            searchHistoryDao.deleteSearchQuery(query.query)
        }
    }

    fun deleteAllQueries(){
        CoroutineScope(Dispatchers.IO).launch { // TODO: move to repo
            searchHistoryDao.nukeTable()
        }
    }

}