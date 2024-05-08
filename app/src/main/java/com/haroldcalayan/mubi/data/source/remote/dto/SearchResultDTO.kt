package com.haroldcalayan.mubi.data.source.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResultDTO(
    val page: Long? = null,
    val results: List<Result>? = null,

    @Json(name = "total_pages")
    val totalPages: Long? = null,

    @Json(name = "total_results")
    val totalResults: Long? = null
)

data class Result (
    val name: String? = null,
    val id: Long? = null
)

