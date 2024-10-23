package com.grv.designsystem.component.organization

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.grv.designsystem.theme.AppTheme

@Composable
fun AppLineDivider(modifier: Modifier = Modifier, colorFilter: ColorFilter? = null) {
    Image(
        painter = painterResource(id = AppTheme.drawables.divider),
        contentDescription = "Divider",
        alignment = Alignment.Center,
        contentScale = ContentScale.FillWidth,
        colorFilter = colorFilter,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    )
}

@Preview
@Composable
private fun AppLinePreview() {
    AppTheme {
        AppLineDivider()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MakeShiftLinePreviewDark() {
    AppTheme {
        AppLineDivider()
    }
}
