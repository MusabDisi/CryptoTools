package com.dissiapps.crypto.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dissiapps.crypto.data.local.fgindex.FGIndexDao
import com.dissiapps.crypto.data.local.fgindex.FGIndexModel

@Database(
    entities = [FGIndexModel::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "cryptoTools_db.db"
    }

    abstract fun getFGIndexDao(): FGIndexDao
}