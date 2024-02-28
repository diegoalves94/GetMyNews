package me.study.getmynews.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import me.study.getmynews.utils.convertDateFormat

@Entity
@JsonClass(generateAdapter = true)
data class Article(
    @PrimaryKey(autoGenerate = true)
    val articleId: Int? = null,
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val publishedAt: String? = null,
    val content: String? = null,
    @Embedded
    val source: Source? = null
) {
    fun getPublishedDate() = "Published at ${convertDateFormat(publishedAt)}"
}