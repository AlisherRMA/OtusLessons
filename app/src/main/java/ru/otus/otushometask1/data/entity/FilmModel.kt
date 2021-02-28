package ru.otus.otushometask1.data.entity

import com.google.gson.annotations.SerializedName

data class Film(
    @SerializedName("poster_path") val image: String,
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String
)

data class PageableResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<Film>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)
