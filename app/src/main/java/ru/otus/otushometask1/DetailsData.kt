package ru.otus.otushometask1

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DetailsData(val isCheckBoxSelected: Boolean, val comment: String): Parcelable {
}