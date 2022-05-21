package com.dissiapps.crypto.data.local.fgindex

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FGIndexDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(data: List<FGIndexModel>)

    @Query("SELECT * FROM fg_index_table")
    fun getFGIndexData(): Flow<List<FGIndexModel>>

    @Query("DELETE FROM fg_index_table")
    suspend fun nukeTable()

}