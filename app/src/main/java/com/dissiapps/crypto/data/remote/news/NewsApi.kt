package com.dissiapps.crypto.data.remote.news

import com.dissiapps.crypto.BuildConfig
import com.dissiapps.crypto.data.models.news.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    companion object {
        const val BASE_URL = "https://cryptopanic.com/"
        private const val AUTH_TOKEN = BuildConfig.NEWS_AUTH_TOKEN
    }

    @GET("/api/v1/posts/")
    suspend fun getNewPosts(
        @Query("page") page: Int,
        @Query("currencies") currencies: List<String> = listOf(),
        @Query("kind") kind: String = "news",
        @Query("public") public: Boolean = true,
        @Query("auth_token") auth: String = AUTH_TOKEN,
    ): NewsResponse

}