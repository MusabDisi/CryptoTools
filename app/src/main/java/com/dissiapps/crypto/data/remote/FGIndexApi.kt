package com.dissiapps.crypto.data.remote

import com.dissiapps.crypto.data.models.fgindex.FGIndexResponse
import retrofit2.http.GET

interface FGIndexApi {

    companion object{
        const val BASE_URL = "https://api.alternative.me/"
    }

    @GET("/fng/")
    suspend fun getFGIndex(): FGIndexResponse

}