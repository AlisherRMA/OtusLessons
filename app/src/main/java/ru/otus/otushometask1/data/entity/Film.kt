package ru.otus.otushometask1.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "films")
data class Film(
    @PrimaryKey @SerializedName("id") val id: Int,
    @ColumnInfo(name = "image") @SerializedName("poster_path") val image: String,
    @ColumnInfo(name = "title") @SerializedName("title") val title: String
)

data class PageableResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<Film>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)
