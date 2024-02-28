package me.study.getmynews.repository

import me.study.getmynews.data.local.datasource.NewsDataSourceImpl
import me.study.getmynews.data.local.entities.Article
import me.study.getmynews.data.remote.datasource.NewsDataSourceRemoteImpl
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private var newsDataSource: NewsDataSourceImpl,
    private var newsSourceRemote: NewsDataSourceRemoteImpl
) {

    suspend fun getNewsData(): Result<List<Article>?> {
        return try {
            val result = newsSourceRemote.getNewsData()
            if (result.isSuccess) {
                persistMovieData(result.getOrNull())
                getNewsLocal()
            } else {
                getNewsLocal()
            }
        } catch (e: Exception) {
            getNewsLocal()
        }
    }

    private suspend fun persistMovieData(newsList: List<Article>?) {
        newsDataSource.clearNewsData()
        newsList?.let {
            newsDataSource.saveNewsData(it)
        }
    }

    private suspend fun getNewsLocal(): Result<List<Article>?> {
        val localResult = newsDataSource.getNewsData()
        return when {
            localResult.isSuccess -> Result.success(localResult.getOrNull())
            else -> Result.failure(Throwable())
        }
    }

    suspend fun getArticleData(articleId: Int): Result<Article?> {
        return try {
            val result = newsDataSource.getArticleData(articleId)
            if (result.isSuccess) {
                result
            } else {
                Result.failure(Throwable())
            }
        } catch (e: Exception) {
            Result.failure(Throwable())
        }
    }
}
