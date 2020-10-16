package com.noemi.android.readme.data

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class RepositoryDetails(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "size") val size: Int,
    @Json(name = "stargazers_count") val stargazers_count: Int,
    @Json(name = "watchers_count") val watchers_count: Int,
    @Json(name = "forks_count") val forks_count: Int,
    @Json(name = "open_issues_count") val open_issues_count: Int
) : Parcelable{
    override fun toString(): String {
        return "RepositoryDetails: id=$id, name=$name, description=$description, size=$size, stargazers_count=$stargazers_count, watchers_count=$watchers_count, forks_count=$forks_count, open_issues_count=$open_issues_count"
    }

}