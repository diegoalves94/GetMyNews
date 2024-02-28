package me.study.getmynews.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.study.getmynews.data.dao.NewsDao
import me.study.getmynews.db.NewsDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideNewsDatabase(@ApplicationContext context: Context): NewsDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            NewsDatabase::class.java,
            "news_database"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideNewsDao(db: NewsDatabase): NewsDao =
        db.getNewsDao()

}