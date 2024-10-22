package com.grv.home.model.remote


data class TopNewsApiResponse(
    val top_news: List<NewsCategoryRemote>,
    val language: String,
    val country: String
)

data class NewsCategoryRemote(
    val news: List<NewsArticleRemote>
)

data class NewsArticleRemote(
    val id: Int,
    val title: String, val text: String? = null,
    val summary: String? = null,
    val url: String,
    val image: String? = null,
    val video: String? = null,
    val publish_date: String,
    val author: String? = null,
    val authors: List<String> = emptyList()
)
