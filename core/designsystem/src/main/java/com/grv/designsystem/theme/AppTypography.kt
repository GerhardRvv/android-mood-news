package com.grv.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.grv.core_designsystem.R.font

@Immutable
data class AppTypography(
    val h01: TextStyle = TextStyle(
        fontFamily = SanFrancisco,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 33.sp
    ),
    val h02: TextStyle = TextStyle(
        fontFamily = SanFrancisco,
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
        lineHeight = 26.sp
    ),
    val body: TextStyle = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
) {
    companion object {
        val SanFrancisco = FontFamily(
            Font(font.san_francisco_bold, FontWeight.Normal),
            Font(font.san_francisco_light, FontWeight.Medium),

        )

        val OpenSans = FontFamily(
            Font(font.open_sans_bold, FontWeight.Medium),
            Font(font.open_sans_regular, FontWeight.SemiBold),
        )
    }
}

internal val LocalTypography = staticCompositionLocalOf { AppTypography() }
