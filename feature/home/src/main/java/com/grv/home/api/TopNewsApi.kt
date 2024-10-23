package com.grv.home.api

import com.grv.home.model.remote.TopNewsApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TopNewsApi {

    //Uncomment for mock response
//    @GET("/GerhardRvv/c7c5dae7b52413593bdfdafd4347109e/raw/79a40e2b6319a746eb3aa4ba31999f34844db228/topNewsMock.json")
//    suspend fun fetchMockResponse(): TopNewsApiResponse

    @GET("/top-news")
    suspend fun fetchTopNews(
        @Query("source-country") sourceCountry: String,
        @Query("language") language: String,
        @Query("date") date: String,
    ): TopNewsApiResponse
}
