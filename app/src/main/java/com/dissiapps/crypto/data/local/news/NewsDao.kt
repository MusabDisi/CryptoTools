package com.dissiapps.crypto.data.local.news

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface NewsDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertNewsList(list: List<NewsModel>)

    @Query("SELECT * FROM news_table")
    fun getNews(): PagingSource<Int, NewsModel>

    @Query("DELETE FROM news_table")
    suspend fun nukeTable()
}