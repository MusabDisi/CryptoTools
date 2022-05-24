package com.dissiapps.crypto.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dissiapps.crypto.data.local.fgindex.FGIndexDao
import com.dissiapps.crypto.data.local.fgindex.FGIndexModel
import com.dissiapps.crypto.data.local.news.NewsDao
import com.dissiapps.crypto.data.local.news.NewsModel

@Database(
    entities = [FGIndexModel::class, NewsModel::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "cryptoTools_db.db"
    }

    abstract fun getFGIndexDao(): FGIndexDao

    abstract fun getNewsDao(): NewsDao
}