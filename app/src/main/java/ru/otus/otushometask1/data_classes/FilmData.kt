package ru.otus.otushometask1.data_classes

import android.graphics.drawable.Drawable
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class FilmData(val id: Int, val name: String, val description: String, val image: Int, var isVisited: Boolean, var isFavorite: Boolean): Parcelable {
}