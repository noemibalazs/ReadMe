package com.noemi.android.readme.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Repositories(
    @Json(name = "total_count") val total_count: Int,
    @Json(name = "items") val items: MutableList<Repository>
) {
    override fun toString(): String {
        return "Repositories: total_count=$total_count, items=$items"
    }
}
