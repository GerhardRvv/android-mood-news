package com.grv.home.util

import com.grv.home.model.presentation.HomeScreenUiState

sealed class HomeUiState {
    data class Success(val listScreenUIState: HomeScreenUiState) : HomeUiState()
    data class Loading(val isRefreshing: Boolean = false) : HomeUiState()
    data class Error(val message: String, val isRefreshing: Boolean = false) : HomeUiState()
}