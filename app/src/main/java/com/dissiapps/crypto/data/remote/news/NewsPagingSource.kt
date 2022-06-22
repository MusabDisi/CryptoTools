package com.dissiapps.crypto.data.remote.news

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dissiapps.crypto.data.local.news.NewsModel

class NewsPagingSource(
    private val newsApi: NewsApi,
    private val currenciesList: List<String>
) : PagingSource<Int, NewsModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsModel> {
        return try {
            val page = params.key ?: 1
            val response = newsApi.getNewPosts(page = page, currencies = currenciesList)
            val data = convertToNewsModel(response.newsResults)
            LoadResult.Page(
                data = data,
                prevKey = null, // Only paging forward.
                nextKey = if(response.next != null) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NewsModel>): Int? {
        return null
    }

}