package com.grv.common.di

import android.content.Context
import androidx.room.Room
import com.grv.common.data.db.MoodNewsDataBase
import com.grv.common.data.db.dao.TopNewsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) : MoodNewsDataBase {
        return Room.databaseBuilder(
            context,
            MoodNewsDataBase::class.java,
            "mood_news_room_db"
        ).build()
    }

    @Provides
    fun providesTopNewsDao(appDataBase: MoodNewsDataBase): TopNewsDao {
        return appDataBase.topNewsDao()
    }
}
