package me.study.getmynews.data.remote.services

import me.study.getmynews.data.remote.response.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("top-headlines")
    suspend fun getNewsList(
        @Query("sources") sources: String,
    ): Response<NewsResponse>

}