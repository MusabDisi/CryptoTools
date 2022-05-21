package com.dissiapps.crypto.data

import android.util.Log
import com.dissiapps.crypto.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.lang.Exception

fun <DataType, RequestType> networkBoundResource(
    localQuery: () -> Flow<DataType>,
    remoteRequest: suspend () -> RequestType,
    saveFetchedData: suspend (RequestType) -> Unit,
    shouldFetchNewData: (DataType) -> Boolean
) = flow<Resource<DataType>> {

    val data = localQuery().first()

    if (shouldFetchNewData(data)) {
        emit(Resource.Loading(data))
        try {
            val response = remoteRequest()
            saveFetchedData(response)
            localQuery().collect {
                emit(Resource.Success(it))
            }
        } catch (ex: Exception) {
            Log.e("TAG", "networkBoundResource: ", ex)
            emit(Resource.Error(ex.message, data))
        }
    } else {
        emit(Resource.Success(data))
    }
}