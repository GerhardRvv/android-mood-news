package com.grv.home.mapper

import com.grv.common.util.Mapper
import com.grv.home.model.domain.NewsArticleDomain
import com.grv.home.model.domain.NewsCategoryDomain
import com.grv.home.model.remote.NewsArticleRemote
import com.grv.home.model.remote.NewsCategoryRemote
import javax.inject.Inject

class TopNewsMapper @Inject constructor(
    private val articleMapper: ArticleMapper
) : Mapper<NewsCategoryRemote, NewsCategoryDomain> {

    override fun map(input: NewsCategoryRemote): NewsCategoryDomain {
        return NewsCategoryDomain(
            newsCategories = input.news.map { article ->
                articleMapper.map(article)
            }
        )
    }
}

class ArticleMapper @Inject constructor() : Mapper<NewsArticleRemote, NewsArticleDomain> {
    override fun map(input: NewsArticleRemote): NewsArticleDomain {
        return NewsArticleDomain(
            id = input.id,
            title = input.title,
            content = input.text,
            sourceUrl = input.url,
            imageUrl = input.image,
            publicationDate = input.publish_date,
            author = input.author
        )
    }
}
