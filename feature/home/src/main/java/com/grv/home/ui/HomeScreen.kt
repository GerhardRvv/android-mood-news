package com.grv.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.grv.designsystem.theme.AppTheme
import com.grv.home.util.HomeUiState
import com.grv.home.viewmodel.HomeViewModel

@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {

    val homeUiState by homeViewModel.homeUiState.collectAsState()

    HomeScreenContent(
        uiState = homeUiState,
        onRefresh = homeViewModel::onRefresh
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreenContent(
    uiState: HomeUiState,
    onRefresh: () -> Unit,
) {

    val isRefreshing = uiState is HomeUiState.Loading
    val pullRefreshState =
        rememberPullRefreshState(refreshing = isRefreshing, onRefresh = onRefresh)
    val lazyListState = rememberLazyListState()

    Column(
        modifier = Modifier
            .padding(horizontal = 6.dp)
            .fillMaxSize()
            .background(AppTheme.colors.bg01),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ListHeader()

        Spacer(modifier = Modifier.height(8.dp))

        when (uiState) {
            is HomeUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pullRefresh(pullRefreshState),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is HomeUiState.Success -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pullRefresh(pullRefreshState)
                ) {
                    LazyColumn(
                        state = lazyListState,
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        uiState.listScreenUIState.listViewContent.forEach { category ->
                            item {
                                LazyRow(modifier = Modifier.fillMaxWidth()) {
                                    category.newsCategories.forEach { article ->
                                        item {
                                            Text(text = article.title)
//                                            NewsCard(
//                                                title = article.title,
//                                                content = article.content,
//                                            )
                                        }
                                    }
                                }

                            }
                        }
                    }
                    PullRefreshIndicator(
                        isRefreshing,
                        pullRefreshState,
                        Modifier.align(Alignment.TopCenter)
                    )
                }

            }

            is HomeUiState.Error -> {
                Text("Failed to load articles", modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}

@Composable
private fun ListHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "Top News")
    }
}

