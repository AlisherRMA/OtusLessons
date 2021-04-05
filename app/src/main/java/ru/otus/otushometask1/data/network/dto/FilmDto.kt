package ru.otus.otushometask1.data.network.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import ru.otus.otushometask1.data.database.entity.Film

@Parcelize
data class FilmDto(
    @SerializedName("id") val id: Int,
    @SerializedName("poster_path") val image: String,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String
) : Parcelable


data class PageableResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<Film>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)