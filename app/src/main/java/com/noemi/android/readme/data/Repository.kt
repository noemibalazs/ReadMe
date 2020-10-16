package com.noemi.android.readme.data

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Repository(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String?,
    @Json(name = "full_name") val full_name: String?,
    @Json(name = "description") val description: String?
) : Parcelable {

    override fun toString(): String {
        return "Repository: id=$id, name=$name, full_name=$full_name, description=$description"
    }
}