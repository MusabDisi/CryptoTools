package com.dissiapps.crypto.data.local.news.search_history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchQuery(query: SearchQuery)

    @Query("SELECT * FROM search_history")
    fun getHistory(): Flow<List<SearchQuery>>

    @Query("DELETE FROM search_history WHERE `query` = :query")
    suspend fun deleteSearchQuery(query: String)

    @Query("DELETE FROM search_history")
    suspend fun nukeTable()

}