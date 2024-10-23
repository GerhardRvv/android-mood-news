package com.grv.designsystem.component.card

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.grv.core_designsystem.R
import com.grv.designsystem.component.text.AppTextField
import com.grv.designsystem.theme.AppTheme

@Composable
fun NewsArticleCard(
    sourceUrl: String,
    title: String,
    content: String?,
    imageUrl: String?,
    publicationDate: String,
    author: String?,
    onArticleClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .widthIn(max = 360.dp)
            .height(240.dp)
            .padding(8.dp)
            .clickable { onArticleClick(sourceUrl) },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.bg02)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = "Product image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(60.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))
                AppTextField(
                    text = title,
                    style = AppTheme.typography.h03,
                    color = AppTheme.colors.text01,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            content?.let { content ->
                AppTextField(
                    text = content,
                    style = AppTheme.typography.body,
                    color = AppTheme.colors.text01,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                author?.let { author ->
                    AppTextField(
                        text = "By $author",
                        style = AppTheme.typography.bodySmall,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))

                AppTextField(
                    text = publicationDate,
                    style = AppTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewNewsArticleCardWithImage() {
    AppTheme {
        NewsArticleCard(
            sourceUrl = "https://example.com",
            title = "Breaking News: Major Event Unfolds",
            content = "This is a short summary of the major event that unfolded. The full article is available through the link provided.",
            imageUrl = "https://via.placeholder.com/150",
            publicationDate = "2024-10-23",
            author = "Jane Doe",
            onArticleClick = { sourceUrl ->
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewNewsArticleCardWithoutImage() {
    AppTheme {
        NewsArticleCard(
            sourceUrl = "https://example.com",
            title = "Another News Article",
            content = "This article does not have an associated image, but it provides important details about recent events.",
            imageUrl = null,
            publicationDate = "2024-10-23",
            author = "John Doe",
            onArticleClick = { sourceUrl ->
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewNewsArticleCardWithoutContent() {
    AppTheme {
        NewsArticleCard(
            sourceUrl = "https://example.com",
            title = "Title Only News Article",
            content = null,
            imageUrl = "https://via.placeholder.com/150",
            publicationDate = "2024-10-23",
            author = null,
            onArticleClick = { sourceUrl ->
            }
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewDarkMode() {
    PreviewNewsArticleCardWithImage()
}
