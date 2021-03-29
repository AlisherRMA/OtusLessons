package ru.otus.otushometask1.data.network.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailsData(val isCheckBoxSelected: Boolean, val comment: String): Parcelable {
}