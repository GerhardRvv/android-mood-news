package com.grv.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grv.common.util.Resource
import com.grv.home.di.HomeUseCaseProvider
import com.grv.home.model.presentation.HomeScreenUiState
import com.grv.home.model.presentation.NewsCategoryUiState
import com.grv.home.util.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCaseProvider: HomeUseCaseProvider,
) : ViewModel() {

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading())
    val homeUiState : StateFlow<HomeUiState> = _homeUiState

    private val _navigateToWebView = MutableStateFlow<String?>(null)
    val navigateToWebView: StateFlow<String?> = _navigateToWebView

    init {
        getTopNews()
    }

    private fun getTopNews(): Job = viewModelScope.launch {
        val currentUiState = getCurrentState()

        setLoadingState(currentUiState)

        homeUseCaseProvider.getTopNewsUseCase.invoke(
            sourceCountry = "us",
            language = "en"
        ).collect { response ->
            handleResponse(response)
        }
    }

    private fun getCurrentState(): HomeScreenUiState? {
        return (_homeUiState.value as? HomeUiState.Success)?.listScreenUIState
    }

    private fun setLoadingState(currentState: HomeScreenUiState?) {
        _homeUiState.value = HomeUiState.Success(
            currentState?.copy() ?: createEmptySuccessState()
        )
    }

    private fun createEmptySuccessState(): HomeScreenUiState {
        return HomeScreenUiState(
            startDate = LocalDate.now(),
            listViewContent = listOf(),
            isRefreshing = true
        )
    }

    private fun handleResponse(
        response: Resource<List<NewsCategoryUiState>>) {
        when (response) {
            is Resource.Loading -> handleLoadingState()
            is Resource.Success -> handleSuccessState(response.data)
            is Resource.Error -> handleErrorState()
        }
    }

    private fun handleLoadingState() {
        _homeUiState.value = HomeUiState.Loading(isRefreshing = true)
    }

    private fun handleSuccessState(
        data: List<NewsCategoryUiState>?,
        isRefreshing: Boolean = false
    ) {
        data?.let { listContent ->
            _homeUiState.value = HomeUiState.Success(
               HomeScreenUiState(
                    startDate = LocalDate.now(),
                    listViewContent = listContent,
                    isRefreshing = isRefreshing
                )
            )
        }
    }

    private fun handleErrorState() {
        _homeUiState.value = HomeUiState.Error("Error loading Articles")
    }

    fun onRefresh() {
        getTopNews()
    }

    fun onArticleClick(url: String) {
        _navigateToWebView.value = url
    }

    fun onWebViewNavigated() {
        _navigateToWebView.value = null
    }
}
