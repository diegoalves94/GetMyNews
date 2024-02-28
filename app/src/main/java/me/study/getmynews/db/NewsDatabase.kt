package me.study.getmynews.db

import androidx.room.Database
import androidx.room.RoomDatabase
import me.study.getmynews.data.dao.NewsDao
import me.study.getmynews.data.local.entities.Article
import me.study.getmynews.data.local.entities.Source

@Database(
    entities = [Article::class, Source::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun getNewsDao(): NewsDao

}