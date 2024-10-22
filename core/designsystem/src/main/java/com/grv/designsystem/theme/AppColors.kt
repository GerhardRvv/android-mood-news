package com.grv.designsystem.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

class AppColors(
    bg01: Color,
    bg02: Color,
    text01: Color,
    text02: Color,
    accent01: Color,
    accent02: Color,
    isLight: Boolean
) {
    var bg01 by mutableStateOf(bg01)
        private set

    var bg02 by mutableStateOf(bg02)
        private set

    var text01 by mutableStateOf(text01)
        private set

    var text02 by mutableStateOf(text02)
        private set

    var accent01 by mutableStateOf(accent01)
        private set

    var accent02 by mutableStateOf(accent02)
        private set

    var isLight by mutableStateOf(isLight)
        private set

    fun copy(
        background01: Color = this.bg01,
        background02: Color = this.bg02,
        text01: Color = this.text01,
        text02: Color = this.text02,
        accent01: Color = this.accent01,
        accent02: Color = this.accent02,
        isLight: Boolean = this.isLight
    ) = AppColors(
        background01,
        background02,
        text01,
        text02,
        accent01,
        accent02,
        isLight
    )

    fun updateColorsFrom(other: AppColors) {
        bg01 = other.bg01
        bg02 = other.bg02
        text01 = other.text01
        text02 = other.text02
        accent01 = other.accent01
        accent02 = other.accent02
        isLight = other.isLight
    }
}

fun lightColors(
    background01: Color = Color(0xFFFFFFFF),
    background02: Color = Color(0xFFF7F7F7),
    text01: Color = Color(0xFF2E2E2E),
    text02: Color = Color(0xFF2D343C),
    accent01: Color = Color(0xFFD93A2B),
    accent02: Color = Color(0xFF1476CC),
) = AppColors(
    bg01 = background01,
    bg02 = background02,
    text01 = text01,
    text02 = text02,
    accent01 = accent01,
    accent02 = accent02,
    isLight = true
)

fun darkColors(
    background01: Color = Color(0xFF000000),
    background02: Color = Color(0xFF202020),
    text01: Color = Color(0xFFDEDEDE),
    text02: Color = Color(0xFFA9A9A9),
    accent01: Color = Color(0xFFD93A2B),
    accent02: Color = Color(0xFF1476CC),
) = AppColors(
    bg01 = background01,
    bg02 = background02,
    text01 = text01,
    text02 = text02,
    accent01 = accent01,
    accent02 = accent02,
    isLight = false
)

internal val LocalColors = staticCompositionLocalOf { lightColors() }