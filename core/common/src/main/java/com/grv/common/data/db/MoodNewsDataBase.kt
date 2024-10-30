package com.grv.common.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.grv.common.data.db.dao.TopNewsDao
import com.grv.common.data.db.entity.NewsArticleEntity
import com.grv.common.data.db.entity.NewsCategoryEntity

@Database(
    entities = [
        NewsArticleEntity::class,
        NewsCategoryEntity::class
    ],
    version = 1
)

abstract class MoodNewsDataBase : RoomDatabase() {
    abstract fun topNewsDao() : TopNewsDao
}
