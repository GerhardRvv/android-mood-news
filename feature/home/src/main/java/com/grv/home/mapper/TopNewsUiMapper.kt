package com.grv.home.mapper

import com.grv.common.util.Mapper
import com.grv.common.util.formatCardDate
import com.grv.home.model.domain.NewsArticleDomain
import com.grv.home.model.domain.NewsCategoryDomain
import com.grv.home.model.presentation.NewsArticleUiState
import com.grv.home.model.presentation.NewsCategoryUiState
import javax.inject.Inject

class TopNewsUiMapper @Inject constructor(
    private val articleUiMapper: ArticleUiMapper
) : Mapper<NewsCategoryDomain, NewsCategoryUiState> {
    override fun map(input: NewsCategoryDomain): NewsCategoryUiState {
        return NewsCategoryUiState(
            newsCategories = input.newsCategories.map { article ->
                articleUiMapper.map(article)
            }
        )
    }
}

class ArticleUiMapper @Inject constructor() : Mapper<NewsArticleDomain, NewsArticleUiState> {
    override fun map(input: NewsArticleDomain): NewsArticleUiState {
        return NewsArticleUiState(
            id = input.id,
            title = input.title,
            content = input.content,
            sourceUrl = input.sourceUrl,
            imageUrl = input.imageUrl,
            publicationDate = input.publicationDate.formatCardDate(),
            author = input.author
        )
    }
}
