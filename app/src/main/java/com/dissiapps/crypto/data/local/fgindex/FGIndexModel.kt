package com.dissiapps.crypto.data.local.fgindex

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fg_index_table")
data class FGIndexModel(
    val timeOfNextUpdate: Long,
    @PrimaryKey
    val timestamp: Long,
    val value: String,
    val valueClassification: String
)