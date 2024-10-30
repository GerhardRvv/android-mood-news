package com.grv.home.api

import com.grv.home.model.remote.TopNewsApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TopNewsApi {

    @GET("/top-news")
    suspend fun fetchTopNews(
        @Query("source-country") sourceCountry: String,
        @Query("language") language: String,
        @Query("date") date: String,
    ): TopNewsApiResponse
}
