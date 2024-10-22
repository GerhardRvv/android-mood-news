package com.grv.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.grv.core_common.R
import com.grv.designsystem.theme.AppTheme

@Composable
fun PlaceHolderScreen() {
    PlaceHolderScreenContent()
}

@Composable
fun PlaceHolderScreenContent() {

    Column(
        modifier = Modifier
            .padding(horizontal = 6.dp)
            .fillMaxSize()
            .background(AppTheme.colors.bg01),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            AppTextField(
                text = stringResource(id = R.string.wip),
                type = AppTheme.typography.h01,
                )
        }
    }
}
