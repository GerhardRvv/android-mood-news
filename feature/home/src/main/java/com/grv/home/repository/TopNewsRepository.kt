package com.grv.home.repository

import android.util.Log
import com.grv.common.util.Resource
import com.grv.home.api.TopNewsApi
import com.grv.home.mapper.TopNewsMapper
import com.grv.home.model.domain.NewsCategoryDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TopNewsRepository @Inject constructor(
    private val topNewsApi: TopNewsApi,
//    private val errorHandler: ErrorHandler,
    private val topNewsMapper: TopNewsMapper,
) {

    fun fetchTopNews(
        sourceCountry: String,
        language: String,
        date: String
    ): Flow<Resource<List<NewsCategoryDomain>>> {
        return flow {
            emit(Resource.Loading())

            val response = topNewsApi.fetchTopNews(
                sourceCountry = sourceCountry,
                language = language,
                date = date
            )

            val mappedResponse = response.top_news.map { article ->
                topNewsMapper.map(article)
            }

            emit(Resource.Success(mappedResponse))
        }.catch { throwable : Throwable ->
            Log.e("TopNewsRepository", "fetchTopNews: ", throwable)
//            val error = errorHandler.handleError(throwable)
            emit(Resource.Error(throwable.message))
        }
    }
}