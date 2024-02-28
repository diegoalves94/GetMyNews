package me.study.getmynews.data.local.datasource

import me.study.getmynews.data.local.entities.Article
import me.study.getmynews.data.remote.datasource.NewsDataSourceRemote

interface NewsDataSource : NewsDataSourceRemote {

    suspend fun getArticleData(articleId: Int) : Result<Article?>

}