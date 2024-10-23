package com.grv.mood_news_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.grv.core_common.R
import com.grv.designsystem.component.PlaceHolderScreen
import com.grv.designsystem.theme.AppTheme
import com.grv.home.ui.HomeScreen
import com.grv.home.ui.WebViewScreen
import com.grv.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeScreenViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                MoodNewsUIApp(homeScreenViewModel)
            }
        }
    }
}

@Composable
fun MoodNewsUIApp(homeScreenViewModel: HomeViewModel) {
    val navController = rememberNavController()

    val navigateToWebView by homeScreenViewModel.navigateToWebView.collectAsState()

    LaunchedEffect(navigateToWebView) {
        navigateToWebView?.let { url ->
            navController.navigate("webview?url=$url")
            homeScreenViewModel.onWebViewNavigated()
        }
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        containerColor = AppTheme.colors.bg01
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { HomeScreen(homeViewModel = homeScreenViewModel) }
            composable(Screen.Search.route) { PlaceHolderScreen() }
            composable(Screen.Profile.route) { PlaceHolderScreen() }
            composable("webview?url={url}") { backStackEntry ->
                val url = backStackEntry.arguments?.getString("url")
                url?.let { WebViewScreen(navController, it) }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        Screen.Home,
        Screen.Search,
        Screen.Profile
    )
    NavigationBar(
        containerColor = AppTheme.colors.bg02
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { screen ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors().copy(
                    selectedIconColor = AppTheme.colors.text01,
                    unselectedIconColor = AppTheme.colors.text02,
                    selectedTextColor = AppTheme.colors.text01,
                    unselectedTextColor = AppTheme.colors.text02,
                    selectedIndicatorColor = AppTheme.colors.accent01

                ),
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = stringResource(id = screen.resourceId)
                    )
                },
                label = { Text(stringResource(id = screen.resourceId)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }

}

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    data object Home : Screen("home", R.string.navigation_home, Icons.Filled.Home)
    data object Search : Screen("search", R.string.navigation_search, Icons.Filled.Search)
    data object Profile : Screen("profile", R.string.navigation_profile, Icons.Filled.Person)
}
