package com.grv.home.usecase

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.grv.common.util.Resource
import com.grv.home.mapper.TopNewsUiMapper
import com.grv.home.model.presentation.NewsCategoryUiState
import com.grv.home.repository.TopNewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date
import javax.inject.Inject

class GetTopNewsUseCase  @Inject constructor(
    private val topNewsRepository: TopNewsRepository,
    private val topNewsUiMapper: TopNewsUiMapper
) {
    suspend operator fun invoke(
        sourceCountry: String,
        language: String,
        date: String = Date().toString()
    ): Flow<Resource<SnapshotStateList<NewsCategoryUiState>>> = flow {
        topNewsRepository.fetchTopNews(
            sourceCountry = sourceCountry,
            language = language,
            date = date
        ).collect { response ->
            when (response) {
                is Resource.Loading -> emit(Resource.Loading())
                is Resource.Success -> {
                    response.data?.let { articles ->
                        val mappedArticles = articles.map { article ->
                            topNewsUiMapper.map(article)
                        }

                        val snapshotStateList =
                            mutableStateListOf(*mappedArticles.toTypedArray())
                        emit(Resource.Success(snapshotStateList))
                    } ?: run {
                        emit(Resource.Success(mutableStateListOf()))
                    }

                }

                is Resource.Error -> {
                    emit(Resource.Error(response.error))
                }
            }
        }
    }
}