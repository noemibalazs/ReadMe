package com.noemi.android.readme.data

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class RepositoryReadMe(
    @Json(name = "content") val content: String?
) : Parcelable {
    override fun toString(): String {
        return "RepositoryReadMe: content=$content)"
    }
}