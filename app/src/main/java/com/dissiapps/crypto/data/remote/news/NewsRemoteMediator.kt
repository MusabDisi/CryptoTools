package com.dissiapps.crypto.data.remote.news

import android.content.Context
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.dissiapps.crypto.data.local.AppDatabase
import com.dissiapps.crypto.data.local.news.NewsModel
import com.dissiapps.crypto.data.models.news.NewsResult
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

private const val NEWS_STARTING_PAGE_INDEX = 1
private const val SHARED_PREFS_NAME = "CryptoTools_SP"
private const val NEXT_PAGE_KEY = "next_page_key"

@ExperimentalPagingApi
class NewsRemoteMediator(
    private val database: AppDatabase,
    private val newsApi: NewsApi,
    context: Context
) : RemoteMediator<Int, NewsModel>() {

    private val sp = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    private val newsDao = database.getNewsDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewsModel>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> NEWS_STARTING_PAGE_INDEX
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> getNextPageKey()
            }

            delay(3000)
            val response = newsApi.getNewPosts(page = page)

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    newsDao.nukeTable()
                }

                //TODO: handle nullability
                val data = convertToNewsModel(response.newsResults)
                val nextPage = page + 1

                newsDao.insertNewsList(data)
                setNextPageKey(nextPage)

            }

            MediatorResult.Success(
                endOfPaginationReached = response.next == null
            )
        } catch (e: IOException) {
            Log.e("TAG", "load: ", e)
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            Log.e("TAG", "load: ", e)
            MediatorResult.Error(e)
        }
    }

    private fun convertToNewsModel(newData: List<NewsResult>): List<NewsModel> {
        return newData.map {
            NewsModel(
                url = it.url,
                created_at = it.created_at,
                title = it.title,
                sourceDomain = it.source.domain,
                currencies = it.currencies)
        }
    }

    private fun getNextPageKey(): Int {
        return sp.getInt(NEXT_PAGE_KEY, 0)
    }

    private fun setNextPageKey(page: Int) {
        with(sp.edit()) {
            putInt(NEXT_PAGE_KEY, page)
        }
    }
}