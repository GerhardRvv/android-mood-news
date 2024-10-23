package com.grv.home.util

import com.grv.home.model.presentation.HomeScreenUiState
import com.grv.home.model.presentation.NewsArticleUiState
import com.grv.home.model.presentation.NewsCategoryUiState
import java.time.LocalDate

object ComposePreviewHelper {

    fun getHomeViewState() = HomeScreenUiState(
        startDate = LocalDate.now(),
        listViewContent = listOf(
            NewsCategoryUiState(
                newsCategories = getNewArticleUiState()
            ),
            NewsCategoryUiState(
                newsCategories = getNewArticleUiState()
            ),
            NewsCategoryUiState(
                newsCategories = getNewArticleUiState()
            ),
            NewsCategoryUiState(
                newsCategories = getNewArticleUiState()
            ),
            NewsCategoryUiState(
                newsCategories = getNewArticleUiState()
            ),
            NewsCategoryUiState(
                newsCategories = getNewArticleUiState()
            )
        )
    )

    fun getNewArticleUiState(): List<NewsArticleUiState>{
       return listOf(
           NewsArticleUiState(
                id = 1,
                sourceUrl = "https://example.com",
                title = "Breaking News: Major Event Unfolds",
                content = "This is a short summary of the major event that unfolded. The full article is available through the link provided.",
                imageUrl = "https://via.placeholder.com/150",
                publicationDate = "2024-10-23",
                author = "Jane Doe",
            ),
           NewsArticleUiState(
               id = 1,
               sourceUrl = "https://example.com",
               title = "Breaking News: Major Event Unfolds",
               content = "This is a short summary of the major event that unfolded. The full article is available through the link provided.",
               imageUrl = "https://via.placeholder.com/150",
               publicationDate = "2024-10-23",
               author = "Jane Doe",
           ),
           NewsArticleUiState(
               id = 1,
               sourceUrl = "https://example.com",
               title = "Breaking News: Major Event Unfolds",
               content = "This is a short summary of the major event that unfolded. The full article is available through the link provided.",
               imageUrl = "https://via.placeholder.com/150",
               publicationDate = "2024-10-23",
               author = "Jane Doe",
           )
        )
    }
}