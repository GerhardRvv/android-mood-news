package com.grv.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember

@Composable
fun AppTheme(
    typography: AppTypography = AppTheme.typography,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = if (darkTheme) darkColors() else lightColors()
    val rememberedColors = remember {
        colors.copy()
    }.apply { updateColorsFrom(colors) }
    val drawables = if (darkTheme) darkDrawables() else lightDrawables()
    val rememberedDrawables = remember {
        drawables.copy()
    }.apply { updateDrawablesFrom(drawables) }

    CompositionLocalProvider(
        LocalColors provides rememberedColors,
        LocalDrawables provides rememberedDrawables,
        LocalTypography provides typography,
        content = content
    )
}

object AppTheme {
    val colors: AppColors
        @Composable
        get() = LocalColors.current
    val drawables: AppDrawables
        @Composable
        get() = LocalDrawables.current
    val typography: AppTypography
        @Composable
        get() = LocalTypography.current
}