package com.dissiapps.crypto.data

import androidx.room.withTransaction
import com.dissiapps.crypto.data.local.AppDatabase
import com.dissiapps.crypto.data.local.fgindex.FGIndexDao
import com.dissiapps.crypto.data.local.fgindex.FGIndexModel
import com.dissiapps.crypto.data.models.fgindex.FGIndexData
import com.dissiapps.crypto.data.remote.FGIndexApi
import com.dissiapps.crypto.utils.Resource
import kotlinx.coroutines.flow.Flow
import java.lang.Exception
import javax.inject.Inject


class Repository @Inject constructor(
    private val fgIndexApi: FGIndexApi,
    private val database: AppDatabase
) {

    private val fgIndexDao: FGIndexDao = database.getFGIndexDao()

    fun getFGIndex(): Flow<Resource<List<FGIndexModel>>> = networkBoundResource(
        localQuery = {
            fgIndexDao.getFGIndexData()
        },
        remoteRequest = {
            fgIndexApi.getFGIndex()
        },
        saveFetchedData = { result ->
            val newData = result.data
            if (newData != null && newData.isNotEmpty()) {
                database.withTransaction {
                    fgIndexDao.nukeTable()
                    fgIndexDao.insertData(convertToStorageModel(newData))
                }
            }
        },
        shouldFetchNewData = {
            shouldFetchNewIndexData(it)
        }
    )

    private fun convertToStorageModel(newData: List<FGIndexData>): List<FGIndexModel> {
        return newData.map {
            FGIndexModel(
                timestamp = try {
                    it.timestamp.toLong() * 1000
                } catch (ex: Exception) {
                    0L
                },
                value = it.value,
                valueClassification = it.value_classification,
                timeOfNextUpdate = System.currentTimeMillis() + try {
                    it.time_until_update.toLong() * 1000
                } catch (ex: Exception) {
                    0L
                }
            )
        }
    }

    private fun shouldFetchNewIndexData(data: List<FGIndexModel>): Boolean {
        return data.isEmpty() || data[0].timeOfNextUpdate <= System.currentTimeMillis()
//        return true
    }

}