package me.study.getmynews.data.remote.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.study.getmynews.data.local.entities.Article
import me.study.getmynews.data.remote.services.NewsService
import me.study.getmynews.utils.Constants
import javax.inject.Inject

class NewsDataSourceRemoteImpl @Inject constructor(
    private var newsService: NewsService
) : NewsDataSourceRemote {

    override suspend fun getNewsData(): Result<List<Article>?> =
        withContext(Dispatchers.IO) {
            val response = newsService.getNewsList(Constants.NEWS_API_NEWS_SOURCE)

            return@withContext when {
                response.isSuccessful -> Result.success(response.body()?.articles)
                else -> Result.failure(Throwable(response.message()))
            }
        }

}