package com.grv.home.mapper

import com.grv.common.data.db.entity.NewsArticleEntity
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

    fun mapFromEntity(entity: NewsArticleEntity): NewsArticleDomain {
        return NewsArticleDomain(
            id = entity.id,
            title = entity.title,
            content = entity.content,
            sourceUrl = entity.sourceUrl,
            imageUrl = entity.imageUrl,
            publicationDate = entity.publicationDate,
            author = entity.author
        )
    }

    fun mapToEntity(article: NewsArticleDomain, categoryId: Long): NewsArticleEntity {
        return NewsArticleEntity(
            id = article.id,
            categoryId = categoryId,
            title = article.title,
            content = article.content,
            sourceUrl = article.sourceUrl,
            imageUrl = article.imageUrl,
            publicationDate = article.publicationDate,
            author = article.author
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
