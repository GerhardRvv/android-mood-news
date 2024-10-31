package com.grv.home.mapper

import com.google.common.truth.Truth.assertThat
import com.grv.common.data.db.entity.NewsArticleEntity
import com.grv.home.model.domain.NewsArticleDomain
import com.grv.home.model.domain.NewsCategoryDomain
import com.grv.home.model.remote.NewsArticleRemote
import com.grv.home.model.remote.NewsCategoryRemote
import org.junit.Before
import org.junit.Test

class TopNewsMapperTest {

    private lateinit var topNewsMapper: TopNewsMapper
    private lateinit var articleMapper: ArticleMapper

    @Before
    fun setUp() {
        articleMapper = ArticleMapper()
        topNewsMapper = TopNewsMapper(articleMapper)
    }

    private fun mockNewsArticleEntity() = NewsArticleEntity(
        id = 1,
        categoryId = 1L,
        title = "Article Title",
        content = "Article Content",
        sourceUrl = "http://example.com",
        imageUrl = "http://example.com/image.jpg",
        publicationDate = "2022-09-01",
        author = "Author Name"
    )

    private fun mockNewsArticleDomain() = NewsArticleDomain(
        id = 1,
        title = "Article Title",
        content = "Article Content",
        sourceUrl = "http://example.com",
        imageUrl = "http://example.com/image.jpg",
        publicationDate = "2022-09-01",
        author = "Author Name"
    )

    private fun mockNewsArticleRemote() = NewsArticleRemote(
        id = 1,
        title = "Remote Article Title",
        text = "Remote Article Content",
        url = "http://example.com/remote",
        image = "http://example.com/remote_image.jpg",
        publish_date = "2022-09-02",
        author = "Remote Author"
    )

    private fun mockNewsCategoryRemote() = NewsCategoryRemote(
        news = listOf(mockNewsArticleRemote())
    )

    @Test
    fun `TopNewsMapper should map NewsCategoryRemote to NewsCategoryDomain`() {
        val expected = NewsCategoryDomain(
            newsCategories = listOf(
                NewsArticleDomain(
                    id = 1,
                    title = "Remote Article Title",
                    content = "Remote Article Content",
                    sourceUrl = "http://example.com/remote",
                    imageUrl = "http://example.com/remote_image.jpg",
                    publicationDate = "2022-09-02",
                    author = "Remote Author"
                )
            )
        )

        val result = topNewsMapper.map(mockNewsCategoryRemote())

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `TopNewsMapper should map NewsArticleEntity to NewsArticleDomain`() {
        val entity = mockNewsArticleEntity()
        val expected = mockNewsArticleDomain()

        val result = topNewsMapper.mapFromEntity(entity)

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `TopNewsMapper should map NewsArticleDomain to NewsArticleEntity`() {
        val domainModel = mockNewsArticleDomain()
        val categoryId = 1L
        val expected = mockNewsArticleEntity()

        val result = topNewsMapper.mapToEntity(domainModel, categoryId)

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `ArticleMapper should map NewsArticleRemote to NewsArticleDomain`() {
        val remoteModel = mockNewsArticleRemote()
        val expected = NewsArticleDomain(
            id = 1,
            title = "Remote Article Title",
            content = "Remote Article Content",
            sourceUrl = "http://example.com/remote",
            imageUrl = "http://example.com/remote_image.jpg",
            publicationDate = "2022-09-02",
            author = "Remote Author"
        )

        val result = articleMapper.map(remoteModel)

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `TopNewsMapper should map NewsCategoryRemote with empty news list to NewsCategoryDomain with empty newsCategories`() {
        val emptyCategoryRemote = NewsCategoryRemote(news = emptyList())
        val expected = NewsCategoryDomain(newsCategories = emptyList())

        val result = topNewsMapper.map(emptyCategoryRemote)

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `TopNewsMapper should map NewsArticleEntity with null fields to NewsArticleDomain with null fields`() {
        val entityWithNulls = NewsArticleEntity(
            id = 2,
            categoryId = 1L,
            title = "Partial Article",
            content = null,
            sourceUrl = "http://example.com/partial",
            imageUrl = null,
            publicationDate = "2022-09-02",
            author = null
        )
        val expected = NewsArticleDomain(
            id = 2,
            title = "Partial Article",
            content = null,
            sourceUrl = "http://example.com/partial",
            imageUrl = null,
            publicationDate = "2022-09-02",
            author = null
        )

        val result = topNewsMapper.mapFromEntity(entityWithNulls)

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `ArticleMapper should map NewsArticleRemote with null fields to NewsArticleDomain with null fields`() {
        val remoteWithNulls = NewsArticleRemote(
            id = 3,
            title = "Remote Article with Nulls",
            text = null,
            url = "http://example.com/remote-null",
            image = null,
            publish_date = "2022-09-03",
            author = null
        )
        val expected = NewsArticleDomain(
            id = 3,
            title = "Remote Article with Nulls",
            content = null,
            sourceUrl = "http://example.com/remote-null",
            imageUrl = null,
            publicationDate = "2022-09-03",
            author = null
        )

        val result = articleMapper.map(remoteWithNulls)

        assertThat(result).isEqualTo(expected)
    }
}
