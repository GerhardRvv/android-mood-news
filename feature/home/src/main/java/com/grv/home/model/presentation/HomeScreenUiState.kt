package com.grv.home.model.presentation

import java.time.LocalDate

data class HomeScreenUiState(
    val startDate: LocalDate,
    val listViewContent: List<NewsCategoryUiState>,
    val isRefreshing: Boolean = false
)
