package com.grv.designsystem.theme

import androidx.annotation.DrawableRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import com.grv.core_designsystem.R

class AppDrawables(
    @DrawableRes divider: Int,
    @DrawableRes back: Int,

) {
    var divider by mutableStateOf(divider)
        private set

    var back by mutableStateOf(back)
        private set


    fun copy(
        divider: Int = this.divider,
        back: Int = this.back,
    ) = AppDrawables(
        divider = divider,
        back = back,
    )

    fun updateDrawablesFrom(other: AppDrawables) {
        divider = other.divider
        back = other.back
    }
}

fun lightDrawables(
    divider: Int = R.drawable.ic_horizontal_divider_dark,
    back: Int = R.drawable.ic_back_button_dark,
) = AppDrawables(
    divider = divider,
    back = back,
)

fun darkDrawables(
    divider: Int = R.drawable.ic_horizontal_divider_light,
    back: Int = R.drawable.ic_back_button_light,
) = AppDrawables(
    divider = divider,
    back = back,
)

internal val LocalDrawables = staticCompositionLocalOf { lightDrawables() }
