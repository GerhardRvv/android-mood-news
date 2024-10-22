package com.grv.home.model.presentation

data class NewsCategoryUiState(
    val newsCategories: List<NewsArticleUiState>
)

data class NewsArticleUiState(
    val id: Int,
    val title: String,
    val content: String?,
    val sourceUrl: String,
    val imageUrl: String?,
    val publicationDate: String,
    val author: String?
)
