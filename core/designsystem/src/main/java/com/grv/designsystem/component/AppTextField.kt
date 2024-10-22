package com.grv.designsystem.component

import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import com.grv.designsystem.theme.AppTheme

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    type: TextStyle = AppTheme.typography.h01,
    text: String,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = AppTheme.colors.text01,
    textDecoration: TextDecoration? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis
) {
    ProvideTextStyle(value = type) {
        Text(
            modifier = modifier,
            text = text,
            color = color,
            textAlign = textAlign,
            textDecoration = textDecoration,
            maxLines = maxLines,
            overflow = overflow
        )
    }
}