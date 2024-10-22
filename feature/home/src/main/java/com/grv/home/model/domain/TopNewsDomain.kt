package com.grv.home.model.domain

data class TopNewsDomain(
    val topNews: List<NewsCategoryDomain>
)

data class NewsCategoryDomain(
    val newsCategories: List<NewsArticleDomain>
)

data class NewsArticleDomain(
    val id: Int,
    val title: String,
    val content: String?,
    val sourceUrl: String,
    val imageUrl: String?,
    val publicationDate: String,
    val author: String?
)
