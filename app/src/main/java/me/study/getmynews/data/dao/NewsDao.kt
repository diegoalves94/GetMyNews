package me.study.getmynews.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import me.study.getmynews.data.local.entities.Article

@Dao
interface NewsDao {

    @Query("SELECT * FROM article")
    suspend fun getNews(): List<Article>?

    @Query("SELECT * FROM article WHERE articleId = :id")
    suspend fun getArticle(id: Int): Article?

    @Query("DELETE FROM article")
    suspend fun clearNewsData()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(newsList: List<Article>)

    @Update
    suspend fun update(article: Article)

    @Update
    suspend fun updateList(newsList: List<Article>)

    @Delete
    suspend fun delete(article: Article)

    @Delete
    suspend fun deleteList(newsList: List<Article>)
}