package com.grv.home.repository

import com.grv.common.data.db.dao.TopNewsDao
import com.grv.common.util.Resource
import com.grv.home.api.TopNewsApi
import com.grv.home.mapper.TopNewsMapper
import com.grv.home.model.domain.NewsCategoryDomain
import com.grv.home.model.remote.NewsCategoryRemote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TopNewsRepository @Inject constructor(
    private val topNewsApi: TopNewsApi,
    private val topNewsMapper: TopNewsMapper,
    private val topNewsDao: TopNewsDao,
) {

    private val cacheExpirationMillis: Long = 20 * 60 * 1000 // 20 minutes

    fun fetchTopNews(
        sourceCountry: String,
        language: String,
        date: String,
        forceRefresh: Boolean
    ): Flow<Resource<List<NewsCategoryDomain>>> = flow {
        emit(Resource.Loading())

        val currentTime = System.currentTimeMillis()
        val hasRecentData = topNewsDao.hasRecentData(cacheExpiryTime = currentTime - cacheExpirationMillis) > 0

        if (hasRecentData && !forceRefresh) {
            val cachedCategories = fetchCachedCategories()
            emit(Resource.Success(cachedCategories))
        } else {
            val response = topNewsApi.fetchTopNews(
                sourceCountry = sourceCountry,
                language = language,
                date = date
            )

            val mappedCategories = cacheFetchedCategories(response.top_news)
            emit(Resource.Success(mappedCategories))
        }
    }.catch { throwable ->
        emit(Resource.Error(throwable.message))
    }

    private suspend fun fetchCachedCategories(): List<NewsCategoryDomain> {
        return topNewsDao.getAllCategories().map { category ->
            val articles = topNewsDao.getArticlesByCategory(category).first()
            NewsCategoryDomain(newsCategories = articles.map { topNewsMapper.mapFromEntity(it) })
        }
    }

    private suspend fun cacheFetchedCategories(categories: List<NewsCategoryRemote>): List<NewsCategoryDomain> {
        return categories.map { category ->
            val uniqueCategoryId = System.currentTimeMillis()
            val categoryDomain = topNewsMapper.map(category)

            val articleEntities = categoryDomain.newsCategories.map { article ->
                topNewsMapper.mapToEntity(article, uniqueCategoryId)
            }

            topNewsDao.insertArticles(articleEntities)
            categoryDomain
        }
    }
}
