package me.study.getmynews.data.remote.response

import com.squareup.moshi.JsonClass
import me.study.getmynews.data.local.entities.Article

@JsonClass(generateAdapter = true)
data class NewsResponse(
    val status: String?,
    val totalResults: Int?,
    val articles: List<Article>?
)