package ru.otus.otushometask1.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "films")
@Parcelize
data class Film(
    @PrimaryKey @SerializedName("id") val id: Int,
    @ColumnInfo(name = "image") @SerializedName("poster_path") val image: String,
    @ColumnInfo(name = "title") @SerializedName("title") val title: String,
    @ColumnInfo(name = "overview") @SerializedName("overview") val overview: String,
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP") val createDate: String
) : Parcelable

@Entity(
    tableName = "favorite_films", foreignKeys = arrayOf(
        ForeignKey(
            entity = Film::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("filmId"),
            onDelete = ForeignKey.CASCADE
        )
    )
)
data class FavoriteFilms(
    @PrimaryKey(autoGenerate = true) @SerializedName("id") val id: Int,
    @ColumnInfo(index = true) val filmId: Int
)

data class PageableResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<Film>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)
