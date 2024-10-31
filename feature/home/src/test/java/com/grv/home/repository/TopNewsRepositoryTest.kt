package com.grv.home.repository

import com.google.common.truth.Truth.assertThat
import com.grv.common.util.Resource
import com.grv.home.api.TopNewsApi
import com.grv.home.mapper.TopNewsMapper
import com.grv.home.model.domain.NewsCategoryDomain
import com.grv.home.model.remote.NewsCategoryRemote
import com.grv.home.model.remote.NewsArticleRemote
import com.grv.common.data.db.dao.TopNewsDao
import com.grv.common.data.db.entity.NewsArticleEntity
import com.grv.home.model.domain.NewsArticleDomain
import com.grv.home.model.remote.TopNewsApiResponse
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class TopNewsRepositoryTest {

    private val topNewsApi: TopNewsApi = mockk(relaxed = true)
    private val topNewsMapper: TopNewsMapper = mockk(relaxed = true)
    private val topNewsDao: TopNewsDao = mockk(relaxed = true)
    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var topNewsRepository: TopNewsRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)
        topNewsRepository = TopNewsRepository(topNewsApi, topNewsMapper, topNewsDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    private fun mockNewsArticleEntity() = NewsArticleEntity(
        id = 1, categoryId = 1L, title = "Cached Article", content = "This is a cached article",
        sourceUrl = "http://example.com", imageUrl = "http://example.com/image.jpg",
        publicationDate = "2022-09-01", author = "Author"
    )

    private fun mockNewsCategoryRemote() = NewsCategoryRemote(
        news = listOf(
            NewsArticleRemote(
                id = 1, title = "Title", text = "Content", url = "http://example.com",
                publish_date = "2022-09-01"
            )
        )
    )

    private fun mockNewsCategoryDomain() = NewsCategoryDomain(
        newsCategories = listOf(
            NewsArticleDomain(
                id = 1, title = "Cached Article", content = "This is a cached article",
                sourceUrl = "http://example.com", imageUrl = "http://example.com/image.jpg",
                publicationDate = "2022-09-01", author = "Author"
            )
        )
    )

    @Test
    fun `fetchTopNews should return cached data when cache is recent and forceRefresh is false`() = runTest {
        coEvery { topNewsDao.hasRecentData(any()) } returns 1
        coEvery { topNewsDao.getAllCategories() } returns listOf(1L)
        coEvery { topNewsDao.getArticlesByCategory(1L) } returns flowOf(listOf(mockNewsArticleEntity()))
        coEvery { topNewsMapper.mapFromEntity(any()) } returns mockNewsCategoryDomain().newsCategories.first()

        val result = topNewsRepository.fetchTopNews("US", "en", "2022-09-01", forceRefresh = false).last()

        assertThat(result).isInstanceOf(Resource.Success::class.java)
        assertThat((result as Resource.Success).data).isEqualTo(listOf(mockNewsCategoryDomain()))
        coVerify(exactly = 0) { topNewsApi.fetchTopNews(any(), any(), any()) }
    }

    @Test
    fun `fetchTopNews should fetch data from API and cache it when cache is expired or forceRefresh is true`() = runTest {
        coEvery { topNewsDao.hasRecentData(any()) } returns 0
        coEvery { topNewsApi.fetchTopNews(any(), any(), any()) } returns TopNewsApiResponse(listOf(mockNewsCategoryRemote()), "en", "US")
        coEvery { topNewsMapper.map(any()) } returns mockNewsCategoryDomain()

        val result = topNewsRepository.fetchTopNews("US", "en", "2022-09-01", forceRefresh = true).last()

        assertThat(result).isInstanceOf(Resource.Success::class.java)
        assertThat((result as Resource.Success).data).isEqualTo(listOf(mockNewsCategoryDomain()))
        coVerify(exactly = 1) { topNewsApi.fetchTopNews(any(), any(), any()) }
    }

    @Test
    fun `fetchTopNews should emit error when API request fails`() = runTest {
        val exception = Exception("API error")
        coEvery { topNewsApi.fetchTopNews(any(), any(), any()) } throws exception

        val result = topNewsRepository.fetchTopNews("US", "en", "2022-09-01", forceRefresh = true).last()

        assertThat(result).isInstanceOf(Resource.Error::class.java)
        assertThat((result as Resource.Error).error).isEqualTo("API error")
    }

    @Test
    fun `fetchTopNews should return empty list when cache is available but empty`() = runTest {
        coEvery { topNewsDao.hasRecentData(any()) } returns 1
        coEvery { topNewsDao.getAllCategories() } returns listOf(1L)
        coEvery { topNewsDao.getArticlesByCategory(1L) } returns flowOf(emptyList())

        val result = topNewsRepository.fetchTopNews("US", "en", "2022-09-01", forceRefresh = false).last()

        assertThat(result).isInstanceOf(Resource.Success::class.java)
        assertThat((result as Resource.Success).data).isEqualTo(listOf(NewsCategoryDomain(newsCategories = emptyList())))
        coVerify(exactly = 0) { topNewsApi.fetchTopNews(any(), any(), any()) }
    }

    @Test
    fun `fetchTopNews should cache data after fetching from API`() = runTest {
        coEvery { topNewsDao.hasRecentData(any()) } returns 0
        coEvery { topNewsApi.fetchTopNews(any(), any(), any()) } returns TopNewsApiResponse(listOf(mockNewsCategoryRemote()), "en", "US")
        coEvery { topNewsMapper.map(any()) } returns mockNewsCategoryDomain()

        val result = topNewsRepository.fetchTopNews("US", "en", "2022-09-01", forceRefresh = false).last()

        assertThat(result).isInstanceOf(Resource.Success::class.java)
        coVerify { topNewsDao.insertArticles(any()) }
    }

    @Test
    fun `fetchTopNews should emit error when caching fails after API fetch`() = runTest {
        coEvery { topNewsDao.hasRecentData(any()) } returns 0
        coEvery { topNewsApi.fetchTopNews(any(), any(), any()) } returns TopNewsApiResponse(listOf(mockNewsCategoryRemote()), "en", "US")
        coEvery { topNewsDao.insertArticles(any()) } throws Exception("Caching Error")

        val result = topNewsRepository.fetchTopNews("US", "en", "2022-09-01", forceRefresh = false).last()

        assertThat(result).isInstanceOf(Resource.Error::class.java)
        assertThat((result as Resource.Error).error).isEqualTo("Caching Error")
    }
}
