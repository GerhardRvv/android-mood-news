package com.grv.common.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_articles")
data class NewsArticleEntity(
    @PrimaryKey val id: Int,
    val categoryId: Long,
    val title: String,
    val content: String?,
    val sourceUrl: String,
    val imageUrl: String?,
    val publicationDate: String,
    val author: String?,
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP") val lastFetched: Long = System.currentTimeMillis()
)