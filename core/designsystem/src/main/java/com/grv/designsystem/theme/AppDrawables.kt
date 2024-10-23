package com.grv.designsystem.theme

import androidx.annotation.DrawableRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import com.grv.core_designsystem.R

class AppDrawables(
    @DrawableRes divider: Int,

) {
    var divider by mutableStateOf(divider)
        private set


    fun copy(
        divider: Int = this.divider,
    ) = AppDrawables(
        divider = divider,
    )

    fun updateDrawablesFrom(other: AppDrawables) {
        divider = other.divider
    }
}

fun lightDrawables(
    divider: Int = R.drawable.ic_horizontal_divider_dark,
) = AppDrawables(
    divider = divider,
)

fun darkDrawables(
    divider: Int = R.drawable.ic_horizontal_divider_light,
) = AppDrawables(
    divider = divider,
)

internal val LocalDrawables = staticCompositionLocalOf { lightDrawables() }
