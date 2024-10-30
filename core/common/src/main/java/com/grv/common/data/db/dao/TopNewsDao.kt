package com.grv.common.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.grv.common.data.db.entity.NewsArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TopNewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<NewsArticleEntity>)

    @Query("SELECT * FROM news_articles WHERE categoryId = :categoryId ORDER BY publicationDate DESC")
    fun getArticlesByCategory(categoryId: Long): Flow<List<NewsArticleEntity>>

    @Query("SELECT COUNT(*) FROM news_articles WHERE lastFetched > :cacheExpiryTime")
    suspend fun hasRecentData(cacheExpiryTime: Long): Int

    @Query("SELECT DISTINCT categoryId FROM news_articles")
    suspend fun getAllCategories(): List<Long>

    @Query("DELETE FROM news_articles WHERE categoryId = :categoryId")
    suspend fun deleteArticlesByCategory(categoryId: Long)
}
