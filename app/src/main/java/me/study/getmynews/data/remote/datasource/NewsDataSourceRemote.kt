package me.study.getmynews.data.remote.datasource

import me.study.getmynews.data.local.entities.Article

interface NewsDataSourceRemote {

    suspend fun getNewsData(): Result<List<Article>?>

    suspend fun saveNewsData(newsList: List<Article>) { /* default implementation */ }

    suspend fun clearNewsData() { /* default implementation */ }

}