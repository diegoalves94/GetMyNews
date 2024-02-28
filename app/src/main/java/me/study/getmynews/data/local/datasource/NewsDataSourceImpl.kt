package me.study.getmynews.data.local.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.study.getmynews.data.dao.NewsDao
import me.study.getmynews.data.local.entities.Article
import javax.inject.Inject

class NewsDataSourceImpl @Inject constructor(
    private var newsDao: NewsDao
) : NewsDataSource {

    override suspend fun getNewsData(): Result<List<Article>?> =
        withContext(Dispatchers.IO) {
            val resultLocal = loadLocalNewsData()

            return@withContext when {
                resultLocal.isNullOrEmpty() -> Result.failure(Throwable())
                else -> Result.success(resultLocal)
            }
        }

    override suspend fun saveNewsData(newsList: List<Article>) {
        newsDao.insertList(newsList)
    }

    override suspend fun clearNewsData() {
        newsDao.clearNewsData()
    }

    private suspend fun loadLocalNewsData() = newsDao.getNews()

    override suspend fun getArticleData(articleId: Int): Result<Article?> =
        withContext(Dispatchers.IO) {
            return@withContext when (val resultLocal = loadLocalArticleData(articleId)) {
                null -> Result.failure(Throwable())
                else -> Result.success(resultLocal)
            }
        }

    private suspend fun loadLocalArticleData(articleId: Int) =
        newsDao.getArticle(articleId)
}