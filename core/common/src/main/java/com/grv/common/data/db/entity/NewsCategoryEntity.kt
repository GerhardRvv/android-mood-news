package com.grv.common.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_categories")
data class NewsCategoryEntity(
    @PrimaryKey val categoryId: Long,
    val language: String,
    val country: String,
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP") val lastFetched: Long = System.currentTimeMillis()
)