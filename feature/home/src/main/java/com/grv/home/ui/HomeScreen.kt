package com.grv.home.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.grv.core_common.R
import com.grv.designsystem.component.card.NewsArticleCard
import com.grv.designsystem.component.organization.AppLineDivider
import com.grv.designsystem.component.text.AppTextField
import com.grv.designsystem.theme.AppTheme
import com.grv.home.util.ComposePreviewHelper
import com.grv.home.util.HomeUiState
import com.grv.home.viewmodel.HomeViewModel

@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {

    val homeUiState by homeViewModel.homeUiState.collectAsState()

    HomeScreenContent(
        uiState = homeUiState,
        onRefresh = homeViewModel::onRefresh,
        onArticleClick = homeViewModel::onArticleClick
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreenContent(
    uiState: HomeUiState,
    onArticleClick: (String) -> Unit = {},
    onRefresh: () -> Unit,
) {

    val isRefreshing = uiState is HomeUiState.Loading
    val pullRefreshState =
        rememberPullRefreshState(refreshing = isRefreshing, onRefresh = onRefresh)

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
                SuccessHomeScreen(
                    uiState = uiState,
                    isRefreshing = isRefreshing,
                    pullRefreshState = pullRefreshState,
                    onArticleClick = onArticleClick
                )
            }

            is HomeUiState.Error -> {
                Text(
                    uiState.message,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SuccessHomeScreen(
    uiState: HomeUiState.Success,
    isRefreshing: Boolean,
    pullRefreshState: PullRefreshState,
    onArticleClick: (String) -> Unit = {}
) {

    val lazyListState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(
            state = lazyListState,
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item(key = "header") {

            }
            uiState.listScreenUIState.listViewContent.forEach { category ->
                item {
                    AppTextField(
                        modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
                        text = category.newsCategories.first().title,
                        style = AppTheme.typography.h01
                    )

                    LazyRow(modifier = Modifier.fillMaxWidth()) {
                        category.newsCategories.forEach { article ->
                            item {
                                NewsArticleCard(
                                    sourceUrl = article.sourceUrl,
                                    title = article.title,
                                    content = article.content,
                                    imageUrl = article.imageUrl,
                                    publicationDate = article.publicationDate,
                                    author = article.author,
                                    onArticleClick = onArticleClick
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
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

@Composable
private fun ListHeader() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AppTextField(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
            text = stringResource(id = R.string.today_top_news),
            style = AppTheme.typography.h01
        )
        AppLineDivider(colorFilter = ColorFilter.tint(AppTheme.colors.accent01))
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomeScreenContent() {
    AppTheme {
        HomeScreenContent(
            uiState = HomeUiState.Success(
                ComposePreviewHelper.getHomeViewState()
            ),
            onRefresh = {}
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewHomeScreenContentDark() {
    PreviewHomeScreenContent()
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomeScreenContentLoading() {
    AppTheme {
        HomeScreenContent(uiState = HomeUiState.Loading(), onRefresh = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomeScreenContentError() {
    AppTheme {
        HomeScreenContent(uiState = HomeUiState.Error("Something went wrong"), onRefresh = {})
    }
}
