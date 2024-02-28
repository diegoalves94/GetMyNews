package me.study.getmynews.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class Source(
    @PrimaryKey
    val id: String,
    val name: String
)
