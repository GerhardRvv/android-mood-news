package com.grv.home.ui

import android.content.res.Configuration
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.grv.core_common.R
import com.grv.designsystem.component.text.AppTextField
import com.grv.designsystem.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebViewScreen(navController: NavController, url: String) {
    BackHandler {
        navController.popBackStack()
    }

    Column(
        modifier = Modifier.background(AppTheme.colors.bg01)
    ) {
        TopAppBar(
            modifier = Modifier.padding(10.dp) ,
            colors = TopAppBarDefaults.topAppBarColors(containerColor = AppTheme.colors.bg01),
            title = { AppTextField(text = stringResource(id = R.string.app_name)) },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(AppTheme.drawables.back),
                        contentDescription = "Back to home",
                        tint = AppTheme.colors.text01
                    )
                }
            }
        )

        AndroidView(factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                loadUrl(url)
            }
        }, modifier = Modifier.fillMaxSize())
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWebViewScreen() {
    val navController = rememberNavController()
    val sampleUrl = "https://www.example.com"

    AppTheme {
        WebViewScreen(navController = navController, url = sampleUrl)
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewWebViewScreenDark() {
    PreviewWebViewScreen()
}

